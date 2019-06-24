import javax.sound.midi.*;
import java.io.*;

// https://devoloper.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-devolopment.com/midi-tutorial.html
class Player{

	private static final int INITIAL_INSTRUMENT_INDEX = 0;
	private static final int INITIAL_INSTRUMENT = 0;
	private static final int INITIAL_BPM = 120;
	private static final int INITIAL_VOLUME = 60;
	private static final int INITIAL_OCTAVE = 0;
	private static final int DEFAULT_MIDI_CHANNEL = 0;
	
	private static final int DO = 60;
	private static final int RE = 62;
	private static final int MI = 64;
	private static final int FA = 65;
	private static final int SOL = 67;
	private static final int LA = 69;
	private static final int SI = 71;
	private static final int INVALID_NOTE = 0;

	private static final int PIANO = 0;
	private static final int GUITAR = 1;
	private static final int BASS = 2;
	private static final int XILOPHONE = 3;
	private static final int HARPSICHORD = 4;
	private static final int TUBULARBELLS = 5;
	private static final int PANFLUTE = 6;
	private static final int CHURCHORGAN = 7;

	private static final int TWO = 2;
	private static final double TENPERCENT = 0.1;

	private static final int VOLUME_INFERIOR_LIMIT = 0;
	private static final int VOLUME_SUPERIOR_LIMIT = 127;
	private static final int OCTAVE_INFERIOR_LIMIT = -24;
	private static final int OCTAVE_SUPERIOR_LIMIT = 24;
	private static final int OCTAVE_OFFSET = 12;
	private static final int INSTRUMENT_SUPERIOR_LIMIT = 126;

	private static final int INITIAL_TICK = 1;
	private static final int TIME_RESOLUTION = 4;

	private static final int NOTEON = 144;
	private static final int NOTEOFF = 128;
	private static final int CHANGEINSTRUMENT = 192;

	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;

	private int instrumentIndex = INITIAL_INSTRUMENT_INDEX;
	private int instrument = INITIAL_INSTRUMENT;
	private int bpm = INITIAL_BPM;
	private int volume = INITIAL_VOLUME;
	private int octave = INITIAL_OCTAVE;
	private int tick = INITIAL_TICK;

	private int note;
	private int oldNote;

	public Player(){
	}

	public void playMusic(String music, String initialInstrument, int bpm){
		try{
			sequencer = MidiSystem.getSequencer(); 
            sequencer.open(); 
            sequence = new Sequence(Sequence.PPQ, TIME_RESOLUTION);
            track = sequence.createTrack();
			createTrack(music, initialInstrument);
			sequencer.setSequence(sequence); 
	        sequencer.setTempoInBPM(bpm);
	        sequencer.start();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	public boolean couldDownloadMusic(String music, String initialInstrument, int bpm, File output){
		try{
            sequence = new Sequence(Sequence.PPQ, TIME_RESOLUTION);
            track = sequence.createTrack();
			createTrack(music, initialInstrument);
			
	        MidiSystem.write(sequence, 0,output);
	        return true;
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	return false;
	    }
	}

	private int getInstrumentIndex(String initialInstrument){
		int instrumentIndex;
		if(initialInstrument.equals("Piano")){
			instrumentIndex = PIANO;
		}
		else if(initialInstrument.equals("Violão")){
			instrumentIndex = GUITAR;
		}
		else if(initialInstrument.equals("Baixo")){
			instrumentIndex = BASS;
		}
		else if(initialInstrument.equals("Xilofone")){
			instrumentIndex = XILOPHONE;
		}
		else if(initialInstrument.equals("Harpsichord")){
			instrumentIndex = HARPSICHORD;
		}
		else if(initialInstrument.equals("Tubular Bells")){
			instrumentIndex = TUBULARBELLS;
		}
		else if(initialInstrument.equals("Pan Flute")){
			instrumentIndex = PANFLUTE;
		}
		else if(initialInstrument.equals("Church Organ")){
			instrumentIndex = CHURCHORGAN;
		}
		else{
			instrumentIndex = PIANO;
		}
		return instrumentIndex;
	}

	public void setInstrument(int instrument){
		this.instrumentIndex = instrument;
		switch(instrument){
			case PIANO:
				this.instrument = 0;
				break;
			case GUITAR:
				this.instrument = 25;
				break;
			case BASS:
				this.instrument = 32;
				break;
			case XILOPHONE:
				this.instrument = 13;
				break;
			case HARPSICHORD: 
				this.instrument = 6;
				break;
			case TUBULARBELLS:
				this.instrument = 14;
				break;
			case PANFLUTE:
				this.instrument = 75;
				break;
			case CHURCHORGAN:
				this.instrument = 19;
				break;
			default:
				this.instrumentIndex = INITIAL_INSTRUMENT_INDEX;
				this.instrument = INITIAL_INSTRUMENT;
				break;
		}
		changeInstrument();
	}

	private boolean isInstrumentValid(int instrument){
		return (instrument < INSTRUMENT_SUPERIOR_LIMIT);
	}

	private void setIntrumentWithOffset(int offset){
		if(isInstrumentValid(this.instrument+offset)){
			this.instrument = this.instrument+offset;
		}
		else{
			this.instrument = PIANO;
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
			sequencer.setTempoInBPM(INITIAL_BPM);
		}
	}

	private boolean isVolumeValid(int volume){
		return (volume > VOLUME_INFERIOR_LIMIT && volume < VOLUME_SUPERIOR_LIMIT);
	}

	public void setVolume(int volume){
		if(isVolumeValid(volume)){
			this.volume = volume;
		}
		else{
			this.volume = INITIAL_VOLUME;
		}
	}

	public int getVolume(){
		return this.volume;
	}

	private boolean isOctaveValid(int octave){
		return (octave > OCTAVE_INFERIOR_LIMIT && octave < OCTAVE_SUPERIOR_LIMIT);
	}

	public void setOctave(int octave){
		if(isOctaveValid(octave)){
			this.octave = octave;
		}
		else{
			this.octave = INITIAL_OCTAVE;
		}
	}

	public int getOctave(){
		return this.octave;
	}

	public void increaseOneOctave(){
		setOctave(getOctave() + OCTAVE_OFFSET);
	}

	public void decreaseOneOctave(){
		setOctave(getOctave() - OCTAVE_OFFSET);
	}

	private void setNote(char note){
		switch(note){
			case 'C':
					this.note = DO;
					break;
			case 'D':
					this.note = RE;
					break;
			case 'E':
					this.note = MI;
					break;
			case 'F':
					this.note = FA;
					break;
			case 'G':
					this.note = SOL;
					break;
			case 'A':
					this.note = LA;
					break;
			case 'B':
					this.note = SI;
					break;
			default:
					this.note = INVALID_NOTE;
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
		if(note != INVALID_NOTE){
			isNote = true;
		}
		else{
			isNote = false;
		}
		return isNote;
	}

 	private void startTick(){
 		setTick(INITIAL_TICK);
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
		this.note = INVALID_NOTE;
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
            message.setMessage(command, DEFAULT_MIDI_CHANNEL, note, volume);
		
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
            message.setMessage(command, DEFAULT_MIDI_CHANNEL, instrument, 0);
		
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
					setInstrument(HARPSICHORD);
					break;
			case '\n':
			case '\r':
					setInstrument(TUBULARBELLS);
					break;
			case ';':
					setInstrument(PANFLUTE);
					break;
			case ',':
					setInstrument(CHURCHORGAN);
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

	public void createTrack(String music, String initialInstrument){
		char[] musicArray = music.toCharArray();
		setInstrument(getInstrumentIndex(initialInstrument));
		startTick();
		for(char character : musicArray){
			if(isNote(character))
				treatNoteCase(character);
			else
				treatNotNoteCase(character);
		}
	}
}