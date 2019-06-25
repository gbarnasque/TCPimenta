

import javax.sound.midi.*;
import java.io.*;

// https://devoloper.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-devolopment.com/midi-tutorial.html
class Player{

	private Sequencer sequencer;

	public void playMusic(String music, String initialInstrument, int bpm){
		try{
			sequencer = MidiSystem.getSequencer(); 
            sequencer.open(); 

            //sequence = new Sequence(Sequence.PPQ, TIME_RESOLUTION);
            //track = sequence.createTrack();
			//createTrack(music, initialInstrument);

			Decoder decoder = new Decoder();

			sequencer.setSequence(decoder.createTrack(music, initialInstrument));
	        sequencer.setTempoInBPM(bpm);
	        sequencer.start();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	public boolean couldDownloadMusic(String music, String initialInstrument, int bpm, File output){
		try{
            //sequence = new Sequence(Sequence.PPQ, TIME_RESOLUTION);
            //track = sequence.createTrack();
			//createTrack(music, initialInstrument);

			Decoder decoder = new Decoder();

	        MidiSystem.write(decoder.createTrack(music, initialInstrument), 0,output);
	        return true;
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	return false;
	    }
	}


}