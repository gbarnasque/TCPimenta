import javax.sound.midi.*;

// https://devoloper.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-devolopment.com/midi-tutorial.html
class Player{

	private static final int BPM_INICIAL = 120;
	private static final int TEMPO_INICIAL = 400;
	private static final double VOLUME_INICIAL = 200.0;
	private static final int OITAVA_INICIAL = 0;
	private static final int INSTRUMENTO_INICIAL = 0;
	private static final int INSTRUMENTO_INDEX_INICIAL = 0;

	private static final int PIANO = 0;
	private static final int VIOLAO = 1;
	private static final int BAIXO = 2;
	private static final int XILOFONE = 3;
	private static final int HARPSICHORD = 4;
	private static final int TUBULARBELLS = 5;
	private static final int PANFLUTE = 6;
	private static final int CHURCHORGAN = 7;

	private static final int DOBRO = 2;
	private static final double DEZPORCENTO = 0.1;

	private static final int LIMITESUPERIOROITAVA = 24;
	private static final int LIMITESUPERIORINSTRUMENTOMIDI = 126;
	private static final int NOTAINVALIDA = -1;

	private Synthesizer synthesizer;
	private Soundbank soundbank;
	private MidiChannel[] midiChannels;
	private MidiChannel midiChannel;
	private Instrument[] instrumentos;

	private int instrumentoIndex;
	private int instrumento;
	private int bpm;
	private int tempo;
	private double volume;
	private int oitava;
	private int nota;
	private int notaAntiga;

	private String musica;

	private void showAll(){
		System.out.println(synthesizer);
		System.out.println(soundbank);
		System.out.println(midiChannel);

		for(int i =0 ; i<instrumentos.length; i++){
			System.out.println(instrumentos[i]);
		}
		System.out.println(instrumentos[instrumento]);
		System.out.println(bpm);
		System.out.println(tempo);
		System.out.println(volume);
		System.out.println(oitava);
		System.out.println(nota);
	}

	public Player(){
		try{
			synthesizer = MidiSystem.getSynthesizer();
			soundbank = synthesizer.getDefaultSoundbank();
			
			synthesizer.open();
			midiChannels = synthesizer.getChannels();
			midiChannel = midiChannels[0];

			instrumentos = soundbank.getInstruments();
			synthesizer.loadAllInstruments(soundbank);

			instrumento = INSTRUMENTO_INICIAL; // Piano
			instrumentoIndex = INSTRUMENTO_INDEX_INICIAL;
			oitava = OITAVA_INICIAL;
			bpm = BPM_INICIAL;
			volume = VOLUME_INICIAL;
			tempo = TEMPO_INICIAL;
			trocaInstrumento();
			synthesizer.close();
			this.showAll();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public Player(String musica, int bpm, String instrumento){
		try{
			synthesizer = MidiSystem.getSynthesizer();
			soundbank = synthesizer.getDefaultSoundbank();
			
			synthesizer.open();
			midiChannels = synthesizer.getChannels();
			midiChannel = midiChannels[0];

			instrumentos = soundbank.getInstruments();
			synthesizer.loadAllInstruments(soundbank);
			
			
			oitava = OITAVA_INICIAL;
			this.bpm = bpm;
			volume = VOLUME_INICIAL;
			tempo = TEMPO_INICIAL;
			this.musica = musica;
			setInstrumento(setInitialInstrumentoIndex(instrumento));
			synthesizer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private int setInitialInstrumentoIndex(String instrumento){
		int index;
		if(instrumento.equals("Piano")){
			index = PIANO;
		}
		else if(instrumento.equals("Violão")){
			index = VIOLAO;
		}
		else if(instrumento.equals("Baixo")){
			index = BAIXO;
		}
		else if(instrumento.equals("Xilofone")){
			index = XILOFONE;
		}
		else if(instrumento.equals("Harpsichord")){
			index = HARPSICHORD;
		}
		else if(instrumento.equals("Tubular Bells")){
			index = TUBULARBELLS;
		}
		else if(instrumento.equals("Pan Flute")){
			index = PANFLUTE;
		}
		else if(instrumento.equals("Church Organ")){
			index = CHURCHORGAN;
		}
		else{
			index = 0;
		}
		return index;
	}

	public void setInstrumento(int instrumento){
		this.instrumentoIndex = instrumento;
		switch(instrumento){
			case PIANO:
				this.instrumento = 0;
				break;
			case VIOLAO:
				this.instrumento = 25;
				break;
			case BAIXO:
				this.instrumento = 32;
				break;
			case XILOFONE:
				this.instrumento = 13;
				break;
			case HARPSICHORD: 
				this.instrumento = 6;
				break;
			case TUBULARBELLS:
				this.instrumento = 14;
				break;
			case PANFLUTE:
				this.instrumento = 75;
				break;
			case CHURCHORGAN:
				this.instrumento = 19;
				break;
			default:
				this.instrumentoIndex = INSTRUMENTO_INDEX_INICIAL;
				this.instrumento = INSTRUMENTO_INICIAL;
				break;
		}
		trocaInstrumento();
	}
	private void setIntrumentoBaseadoEmOffset(int offset){
		this.instrumento = this.instrumento + offset;
		if(this.instrumento > LIMITESUPERIORINSTRUMENTOMIDI){
			this.instrumento = 0;
		}
		trocaInstrumento();
	}
	public Instrument getInstrumento(){
		return this.instrumentos[this.instrumento];
	}
	private void trocaInstrumento(){
		midiChannel.programChange(instrumentos[this.instrumento].getPatch().getProgram());
	}

	public void setBPM(int bpm){
		if(bpm > 0){
			this.bpm = bpm;
		}
		else{
			this.bpm = BPM_INICIAL;
		}
	}
	public int getBPM(){
		return this.bpm;
	}

	public void setTempo(int tempo){
		if(tempo > 0){
			this.tempo = tempo;
		}
		else{
			this.tempo = TEMPO_INICIAL;
		}
	}

	public void setVolume(double vol){
		if(vol > 0){
			this.volume = vol;
		}
		else{
			this.volume = VOLUME_INICIAL;
		}
	}
	public double getVolume(){
		return this.volume;
	}

	public void setOitava(int oitava){
		this.oitava = oitava;
	}
	public int getOitava(){
		return this.oitava;
	}
	public void aumentaUmaOitava(){
		if(this.oitava <= LIMITESUPERIOROITAVA){
			this.oitava += 12; // Aumento de oitava no midi
		}
		else{
			this.oitava = OITAVA_INICIAL;
		}
	}
	public void diminuiUmaOitava(){
		this.oitava -= 12; // Diminuição de oitava no midi
	}
	

	private void setNota(char nota){
		switch(nota){
			case 'C': // Dó
					this.nota = 60;
					break;
			case 'D': // Ré
					this.nota = 62;
					break;
			case 'E': // Mi
					this.nota = 64;
					break;
			case 'F': // Fá
					this.nota = 65;
					break;
			case 'G': // Sol
					this.nota = 67;
					break;
			case 'A': // Lá
					this.nota = 69;
					break;
			case 'B': // Sí
					this.nota = 71;
					break;
			default:
					this.nota = NOTAINVALIDA;
					break;
		}
	}
	
	private boolean isNota(char nota){
		boolean isNota;
		switch(nota){
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
					isNota = true;
					break;
			default:
					isNota = false;
					break;
		}
		return isNota;
	}
	private boolean isNota(int nota){
		boolean isNota;
		if(nota != NOTAINVALIDA){
			isNota = true;
		}
		else{
			isNota = false;
		}
		return isNota;
	}
	private void playNota(char nota){
		setNota(nota);
		midiChannel.noteOn(this.nota+oitava,(int)volume);
	}
	private void playNotaAntiga(){
		if(isNota(this.notaAntiga)){
			midiChannel.noteOn(this.notaAntiga+oitava,(int)volume);
		}
	}

	private void openSynthesizer(){
		try{
			synthesizer.open();
			Thread.sleep(synthesizer.getLatency()/1000); // Espera a latencia para o synthesizer funcionar normalmente após aberto
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void closeSynthesizer(){
		try{
			Thread.sleep(1000); // Espera 1000ms=1s para fechar o synthesizer e nÃo terminar a música abruptamente
			synthesizer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void playMusic(){
		char[] notasArray = musica.toCharArray();
		
		setTempo(calculaTempoEntreNotas(contaNotas(notasArray)));
		try{
			openSynthesizer();
			setInstrumento(this.instrumentoIndex);

			for(char caracter : notasArray){
				if(isNota(caracter)){
					playNota(caracter);
					Thread.sleep(this.tempo); // Espera o tempo entre notas
				}
				else{
					this.notaAntiga = this.nota;
					this.nota = NOTAINVALIDA;
					switch(caracter){
					case ' ':
							setVolume(DOBRO*this.volume);
							break;
					case 'o':
					case 'O':
					case 'i':
					case 'I':
					case 'u':
					case 'U':
							setVolume(this.volume + (DEZPORCENTO*this.volume));
							break;
					case '?':
					case '.':
							aumentaUmaOitava();
							break;
					case '!':
							setInstrumento(HARPSICHORD);
							break;
					case '\n':	// Novas linhas (windows e linux)
					case '\r':
							setInstrumento(TUBULARBELLS);
							break;
					case ';':
							setInstrumento(PANFLUTE);
							break;
					case ',':
							setInstrumento(CHURCHORGAN);
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
							int offset = Character.getNumericValue(caracter);
							setIntrumentoBaseadoEmOffset(offset);
							break;
					default:
							playNotaAntiga();
							Thread.sleep(this.tempo); // Espera o tempo entre notas
							break;
					}
				}
			}
			closeSynthesizer();			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private int contaNotas(char[] arr){
		int contagem = 0;
		for(char c : arr){
			switch(c){
				case ' ':
				case 'o':
				case 'O':
				case 'i':
				case 'I':
				case 'u':
				case 'U':
				case '?':
				case '.':
				case '!':
				case '\n':	// Novas linhas (windows e linux)
				case '\r':
				case ';':
				case ',':
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
						this.nota = NOTAINVALIDA;
						this.notaAntiga = NOTAINVALIDA;
						break;
				default:
						if(isNota(c)){
							contagem++;
						}
						else if(isNota(this.notaAntiga)){
							contagem++;
						}
						setNota(c);
						this.notaAntiga = this.nota;
						break;
			}		
		}
		return contagem;
	}
	private int calculaTempoEntreNotas(int quantidade){
		return quantidade*60*1000/(this.bpm*(quantidade-1)); // Expressão utilizada para calcular o tempo entre as notas dado bpm da música e quantidade de notas
	}
}
