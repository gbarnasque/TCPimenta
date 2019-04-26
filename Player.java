import java.util.*;
import java.io.*;
import javax.swing.*; // Biblioteca do Swing para interfaces gráficas
import java.awt.event.*;

import javax.sound.midi.*;

// https://developer.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-development.com/midi-tutorial.html
public class Player{

	public static void main(String[] args) {
		//new Player(args[0]);
		try{
			Synthesizer syn = MidiSystem.getSynthesizer();
			syn.open();
			MidiChannel[] mc = syn.getChannels();

			Instrument[] instr = syn.getDefaultSoundbank().getInstruments();

			syn.loadInstrument(instr[90]);

			JFrame frame = new JFrame("Sound1");                
			JPanel pane = new JPanel();                         
			JButton button1 = new JButton("Click me!");            
			frame.getContentPane().add(pane);                   
			pane.add(button1);                                     
			frame.pack();                                       
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);                                       
			button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  
				mc[5].noteOn(60,600);
			}});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
}