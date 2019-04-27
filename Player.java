import java.util.*;
import java.io.*;
import javax.swing.*; // Biblioteca do Swing para interfaces gráficas
import java.awt.event.*;

import javax.sound.midi.*;

// https://developer.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-development.com/midi-tutorial.html
public class Player{

	public static int j;
	public static void main(String[] args) {
		//new Player(args[0]);
		try{
			Synthesizer syn = MidiSystem.getSynthesizer();
			syn.open();
			MidiChannel[] mc = syn.getChannels();

			Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
			// Instrument[] instr = syn.getAvailableInstruments();

			for(int i=0; i<instr.length; i++){
				System.out.print(i);
				System.out.println(instr[i]);
				//syn.loadInstrument(instr[i]);
			}
			

			//syn.loadInstrument(instr[73]);	

			JFrame frame = new JFrame("Sound1");                
			JPanel pane = new JPanel();
			frame.getContentPane().add(pane); 
			JButton[] buttons = new JButton[128];

			//mc[5].programChange(instr[2].getPatch().getProgram());
			for(j=0;j<1;j++){
			String jota = Integer.toString(j);
			buttons[j] = new JButton(jota);            
			                  
			pane.add(buttons[j]);                                     
			mc[7].programChange(instr[0].getPatch().getProgram());
			//System.out.println(instr[j].getPatch().getProgram());
			buttons[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {  
					mc[7].noteOn(80,100);
					/* 
						Dó - 60
						Ré - 63
						Mi - 66
						Fá - 69
						Sol - 72
						Lá - 75
						Sí - 77
						Dó - 80
					*/
				}
			});
			}
			frame.pack();                                       
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);                                       
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
}