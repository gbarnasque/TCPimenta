
import javax.sound.midi.*;
import java.io.*;

public class Decoder{

	public static final int TWO = 2;
	public static final double TENPERCENT = 0.1;

	public static final int INITIAL_TICK = 1;
	public static final int TIME_RESOLUTION = 4;

	public static final int NOTEON = 144;
	public static final int NOTEOFF = 128;
	public static final int CHANGEINSTRUMENT = 192;

	private String musicToDecode;
	private int[] musicDecoded;

	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;

	private int instrumentIndex = Initial.INITIAL_INSTRUMENT_INDEX.Value();
	private int instrument = Initial.INITIAL_INSTRUMENT.Value();
	private int bpm = Initial.INITIAL_BPM.Value();
	private int volume = Initial.INITIAL_VOLUME.Value();
	private int octave = Initial.INITIAL_OCTAVE.Value();
	private int tick = Initial.INITIAL_TICK.Value();

	private int note;
	private int oldNote;

	public Decoder() throws InvalidMidiDataException{
		sequence = new Sequence(Sequence.PPQ, TIME_RESOLUTION);
		track = sequence.createTrack();
	}

	public Sequence createTrack(String music, String initialInstrument){
		char[] musicArray = music.toCharArray();
		setInstrument(getInstrumentIndex(initialInstrument));
		startTick();
		for(char character : musicArray){
			if(isNote(character))
				treatNoteCase(character);
			else
				treatNotNoteCase(character);
		}

		return this.sequence;
	}

	public void setInstrument(int instrument){
		this.instrumentIndex = instrument;
		if (instrument == Instruments.PIANO.Value()) {
			this.instrument = 0;
		}else if (instrument == Instruments.GUITAR.Value()) {
			this.instrument = 25;
		}else if (instrument == Instruments.BASS.Value()){
			this.instrument = 32;
		}else if (instrument == Instruments.XILOPHONE.Value()){
			this.instrument = 13;
		}else if (instrument == Instruments.HARPSICHORD.Value()) {
			this.instrument = 6;
		}else if (instrument == Instruments.TUBULARBELLS.Value()) {
			this.instrument = 14;
		}else if (instrument == Instruments.PANFLUTE.Value()){
			this.instrument = 75;
		}else if (instrument == Instruments.CHURCHORGAN.Value()){
			this.instrument = 19;
		}else{
			this.instrumentIndex = Initial.INITIAL_INSTRUMENT_INDEX.Value();
			this.instrument = Initial.INITIAL_INSTRUMENT.Value();
		}
		changeInstrument();
	}

	private int getInstrumentIndex(String initialInstrument){
		int instrumentIndex;
		if(initialInstrument.equals("Piano")){
			instrumentIndex = Instruments.PIANO.Value();
		}
		else if(initialInstrument.equals("Violão")){
			instrumentIndex = Instruments.GUITAR.Value();
		}
		else if(initialInstrument.equals("Baixo")){
			instrumentIndex = Instruments.BASS.Value();
		}
		else if(initialInstrument.equals("Xilofone")){
			instrumentIndex = Instruments.XILOPHONE.Value();
		}
		else if(initialInstrument.equals("Harpsichord")){
			instrumentIndex = Instruments.HARPSICHORD.Value();
		}
		else if(initialInstrument.equals("Tubular Bells")){
			instrumentIndex = Instruments.TUBULARBELLS.Value();
		}
		else if(initialInstrument.equals("Pan Flute")){
			instrumentIndex = Instruments.PANFLUTE.Value();
		}
		else if(initialInstrument.equals("Church Organ")){
			instrumentIndex = Instruments.CHURCHORGAN.Value();
		}
		else{
			instrumentIndex = Instruments.PIANO.Value();
		}
		return instrumentIndex;
	}

	private void startTick(){
		setTick(Initial.INITIAL_TICK.Value());
	}

	public void setTick(int tick){
		this.tick = tick;
	}

	public int getTick(){
		return this.tick;
	}

	private void updateTick(){
		setTick(getTick() + TIME_RESOLUTION);
	}

	public void setOldNote(int oldNote){
		this.oldNote = oldNote;
	}

	public int getOldNote(){
		return this.oldNote;
	}

	private void updateOldNote(){
		setOldNote(getNote());
	}


	private void invalidateNote(){
		this.note = Notes.INVALID_NOTE.Note();
	}

	private void setNoteOnTrack(){
		this.track.add(createEvent(NOTEON, getNote() + getOctave(), getVolume(), getTick()));
		this.track.add(createEvent(NOTEOFF, getNote() + getOctave(), getVolume(), getTick() + TWO));
	}

	private void setOldNoteOnTrack(){
		this.track.add(createEvent(NOTEON, getOldNote() + getOctave(), getVolume(), getTick()));
		this.track.add(createEvent(NOTEOFF, getOldNote() + getOctave(), getVolume(), getTick() + TWO));
	}

	private MidiEvent createEvent(int command, int note, int volume, int tick){
		MidiEvent event = null;
		try{
			ShortMessage message = new ShortMessage();
			message.setMessage(command, Initial.DEFAULT_MIDI_CHANNEL.Value(), note, volume);

			event = new MidiEvent(message, tick);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return event;
	}

	private MidiEvent createEvent(int command, int instrument, int tick){
		MidiEvent event = null;
		try{
			ShortMessage message = new ShortMessage();
			message.setMessage(command, Initial.DEFAULT_MIDI_CHANNEL.Value(), instrument, 0);

			event = new MidiEvent(message, tick);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return event;
	}

	private void treatNoteCase(char character){
		setNote(character);
		setNoteOnTrack();
		updateTick();
	}

	private void treatNotNoteCase(char character){
		updateOldNote();
		invalidateNote();
		switch(character){
			case ' ':
				setVolume(TWO*getVolume());
				break;
			case 'o':
			case 'O':
			case 'i':
			case 'I':
			case 'u':
			case 'U':
				setVolume(getVolume() + (int)(TENPERCENT*getVolume()));
				break;
			case '?':
			case '.':
				increaseOneOctave();
				break;
			case '!':
				setInstrument(Instruments.HARPSICHORD.Value());
				break;
			case '\n':
			case '\r':
				setInstrument(Instruments.TUBULARBELLS.Value());
				break;
			case ';':
				setInstrument(Instruments.PANFLUTE.Value());
				break;
			case ',':
				setInstrument(Instruments.CHURCHORGAN.Value());
				break;
			case'0':
			case'1':
			case'2':
			case'3':
			case'4':
			case'5':
			case'6':
			case'7':
			case'8':
			case'9':
				int offset = Character.getNumericValue(character);
				setIntrumentWithOffset(offset);
				break;
			default:
				setOldNoteOnTrack();
				updateTick();
				break;
		}
	}

	private boolean isInstrumentValid(int instrument){
		return (instrument < Limits.INSTRUMENT_SUPERIOR_LIMIT.Value());
	}

	private void setIntrumentWithOffset(int offset){
		if(isInstrumentValid(this.instrument+offset)){
			this.instrument = this.instrument+offset;
		}
		else{
			this.instrument = Instruments.PIANO.Value();
		}
		changeInstrument();
	}

	public int getInstrument(){
		return this.instrument;
	}
	private void changeInstrument(){
		track.add(createEvent(CHANGEINSTRUMENT, getInstrument(), getTick()));
	}

	private boolean isBPMValid(int bpm){
		return (bpm > 0);
	}

	public void setBPM(int bpm){
		if(isBPMValid(bpm)){
			sequencer.setTempoInBPM(bpm);
		}
		else{
			sequencer.setTempoInBPM(Initial.INITIAL_BPM.Value());
		}
	}

	private boolean isVolumeValid(int volume){
		return (volume > Limits.VOLUME_INFERIOR_LIMIT.Value() && volume < Limits.VOLUME_SUPERIOR_LIMIT.Value());
	}

	public void setVolume(int volume){
		if(isVolumeValid(volume)){
			this.volume = volume;
		}
		else{
			this.volume = Initial.INITIAL_VOLUME.Value();
		}
	}

	public int getVolume(){
		return this.volume;
	}

	private boolean isOctaveValid(int octave){
		return (octave > Limits.OCTAVE_INFERIOR_LIMIT.Value() && octave < Limits.OCTAVE_SUPERIOR_LIMIT.Value());
	}

	public void setOctave(int octave){
		if(isOctaveValid(octave)){
			this.octave = octave;
		}
		else{
			this.octave = Initial.INITIAL_OCTAVE.Value();
		}
	}

	public int getOctave(){
		return this.octave;
	}

	public void increaseOneOctave(){
		setOctave(getOctave() + Limits.OCTAVE_OFFSET.Value());
	}

	public void decreaseOneOctave(){
		setOctave(getOctave() - Limits.OCTAVE_OFFSET.Value());
	}

	private void setNote(char note){
		switch(note){
			case 'C':
				this.note = Notes.DO.Note();
				break;
			case 'D':
				this.note = Notes.RE.Note();
				break;
			case 'E':
				this.note = Notes.MI.Note();
				break;
			case 'F':
				this.note = Notes.FA.Note();
				break;
			case 'G':
				this.note = Notes.SOL.Note();
				break;
			case 'A':
				this.note = Notes.LA.Note();
				break;
			case 'B':
				this.note = Notes.SI.Note();
				break;
			default:
				this.note = Notes.INVALID_NOTE.Note();
				break;
		}
	}
	public int getNote(){
		return this.note;
	}

	private boolean isNote(char note){
		boolean isNote;
		switch(note){
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
				isNote = true;
				break;
			default:
				isNote = false;
				break;
		}
		return isNote;
	}
	private boolean isNote(int note){
		boolean isNote;
		if(note != Notes.INVALID_NOTE.Note()){
			isNote = true;
		}
		else{
			isNote = false;
		}
		return isNote;
	}
}