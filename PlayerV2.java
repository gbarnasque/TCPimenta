import javax.sound.midi.*;
import java.io.*;

// https://devoloper.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-devolopment.com/midi-tutorial.html
class PlayerV2{

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


	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;
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

	public static void main(String[] args) {
		PlayerV2 player = new PlayerV2();
	}

	public PlayerV2(){
		try{
			sequencer = MidiSystem.getSequencer(); 
            sequencer.open(); 
  			
            // Creating a sequence. 
            sequence = new Sequence(Sequence.PPQ, 2);

            track = sequence.createTrack();


            int nota = 60;
            for (int i=1; i<(4*10)+1; i+=4){ 
	            // Add Note On event 
	            track.add(createEvent(144, nota, 126, i)); 
	  
	            // Add Note Off event 
	            track.add(createEvent(128, nota, 126, i+2));
            }

            sequencer.setSequence(sequence); 
  
            // Specifies the beat rate in beats per minute.

            sequencer.setTempoInBPM(this.bpm);
            // Sequencer starts to play notes 
            
            sequencer.start();

            
        }
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
	}
	private MidiEvent createEvent(int comando, int nota, int volume, int tick){
		MidiEvent evento = null;
		final int canal = 0;
		try{
            ShortMessage mensagem = new ShortMessage(); 
            mensagem.setMessage(comando, canal, nota, volume);
		
            evento = new MidiEvent(mensagem, tick);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return evento;
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
}