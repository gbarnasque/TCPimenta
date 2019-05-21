import javax.sound.midi.*;

// https://devoloper.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-devolopment.com/midi-tutorial.html
class Player{

	private static final int BPM_INICIAL = 120;
	private static final int TEMPO_INICIAL = 400;
	private static final int VOLUME_INICIAL = 200;
	private static final int OITAVA_INICIAL = 0;
	private static final int INSTRUMENTO_INICIAL = 0;
	private static final int INSTRUMENTO_INDEX_INICIAL = 0;

	private static final int PIANO = 0;
	private static final int VIOLAO = 1;
	private static final int BAIXO = 2;
	private static final int XILOFONE = 3;

	private Synthesizer synthesizer;
	private Soundbank soundbank;
	private MidiChannel[] midiChannels;
	private MidiChannel midiChannel;
	private Instrument[] instrumentos;

	private int instrumentoIndex;
	private int instrumento;
	private int bpm;
	private int tempo;
	private int volume;
	private int oitava;
	private int nota;

	private String musica;

	private void showAll(){
		System.out.println(synthesizer);
		System.out.println(soundbank);
		System.out.println(midiChannel);

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
			midiChannel.programChange(instrumentos[instrumento].getPatch().getProgram());
			synthesizer.close();
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
			
			setInstrumento(setInstrumentoIndex(instrumento));
			oitava = OITAVA_INICIAL;
			this.bpm = bpm;
			volume = VOLUME_INICIAL;
			tempo = TEMPO_INICIAL;
			this.musica = musica;
			midiChannel.programChange(instrumentos[this.instrumento].getPatch().getProgram());
			synthesizer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private int setInstrumentoIndex(String instrumento){
		int index;
		if(instrumento.equals("Piano")){
			index = 0;
		}
		else if(instrumento.equals("Violão")){
			index = 1;
		}
		else if(instrumento.equals("Baixo")){
			index = 2;
		}
		else if(instrumento.equals("Xilofone")){
			index = 3;
		}
		else{
			index = 0;
		}
		return index;
	}

	public void setInstrumento(int instrumento){
		this.instrumentoIndex = instrumento;
		switch(instrumento){
			case PIANO: // Piano
				this.instrumento = 0;
				break;
			case VIOLAO: // Violão
				this.instrumento = 25;
				break;
			case BAIXO: // Baixo
				this.instrumento = 32;
				break;
			case XILOFONE: // Xilofone
				this.instrumento = 13;
				break;
			default:
				this.instrumentoIndex = INSTRUMENTO_INDEX_INICIAL;
				this.instrumento = INSTRUMENTO_INICIAL;
		}
		trocaInstrumento();
	}
	public Instrument getInstrumento(){
		return instrumentos[this.instrumento];
	}
	private void trocaInstrumento(){
		midiChannel.programChange(instrumentos[this.instrumento].getPatch().getProgram());
	}

	public void setBPM(int bpm){
		if(bpm > 0){
			this.bpm = bpm;
		}
		else{
			this.bpm = 1;
		}
	}
	public int getBPM(){
		return bpm;
	}

	public void setTempo(int tempo){
		if(tempo > 0){
			this.tempo = tempo;
		}
		else{
			this.tempo = 1;
		}
	}

	public void setVolume(int vol){
		if(vol > 0){
			this.volume = vol;
		}
		else{
			this.volume = 1;
		}
	}
	public int getVolume(){
		return volume;
	}

	public void setOitava(int oitava){
		this.oitava = oitava;
	}
	public int getOitava(){
		return this.oitava;
	}
	public void aumentaUmaOitava(){
		this.oitava += 12; // Aumento de oitava no midi
	}
	public void diminuiUmaOitava(){
		this.oitava -= 12; // Diminuição de oitava no midi
	}
	

	private void setNota(char nota){
		switch(nota){
			case 'c': // Dó
					this.nota = 60;
					break;
			case 'd': // Ré
					this.nota = 62;
					break;
			case 'e': // Mi
					this.nota = 64;
					break;
			case 'f': // Fá
					this.nota = 65;
					break;
			case 'g': // Sol
					this.nota = 67;
					break;
			case 'a': // Lá
					this.nota = 69;
					break;
			case 'b': // Sí
					this.nota = 71;
					break;
			case ' ': // Espaço
					this.nota = -1;
			default:
		}
	}
	
	private void playNote(char nota){
		setNota(nota);
		midiChannel.noteOn(this.nota+oitava,volume);
	}

	private void openSynthesizer(){
		try{
			synthesizer.open();
			Thread.sleep(synthesizer.getLatency()/1000); // Espera a latencia para o synthesizer funcionar normalmente apÃ³s aberto
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void closeSynthesizer(){
		try{
			Thread.sleep(1000); // Espera 1000ms=1s para fechar o synthesizer e nÃ£o terminar a mÃºsica abruptamente
			synthesizer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void playMusic(){
		musica = this.musica.toLowerCase();
		char[] notasArray = musica.toCharArray();

		int contagemDeNotas = contaNotas(notasArray);
		
		setTempo(calculaTempoEntreNotas(contagemDeNotas));
		try{
			openSynthesizer();
			setInstrumento(this.instrumentoIndex);

			for(char caracter : notasArray){
				switch(caracter){
					case 'a':
					case 'b':
					case 'c':
					case 'd':
					case 'e':
					case 'f':
					case 'g':
					case ' ':
					case 'o':
					case 'i':
					case 'u':
							playNote(caracter);
							contagemDeNotas--;
							Thread.sleep(this.tempo); // Espera o tempo entre notas
							break;
					case '+':
							setVolume(this.volume*2);
							break;
					case '-':
							setVolume(this.volume/2);
							break;
					case 'y': // O+
							aumentaUmaOitava();
							break;
					case 'z': // O-
							diminuiUmaOitava();
							break;
					case '?':
					case '.':
							setOitava(OITAVA_INICIAL);
							setVolume(VOLUME_INICIAL);
							break;
					case '\n':	// Novas linhas (windows e linux)
					case '\r':
							setInstrumento(this.instrumentoIndex + 1); // Rotaciona os intrumentos em ordem
							break;
					case'v': // B+
							setBPM(this.bpm+50);
							setTempo(calculaTempoEntreNotas(contagemDeNotas));
							break;
					case'x': // B-
							setBPM(this.bpm-50);
							setTempo(calculaTempoEntreNotas(contagemDeNotas));
							break;
					default:
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
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case ' ':
				case 'o':
				case 'i':
				case 'u':
						contagem++;
						break;
				default:
						break;
			}
		}
		return contagem;
	}
	private int calculaTempoEntreNotas(int quantidade){
		return quantidade*60*1000/(this.bpm*(quantidade-1)); // Expressão utilizada para calcular o tempo entre as notas dado bpm da música e quantidade de notas
	}
}
