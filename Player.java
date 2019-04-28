import java.util.*;
import java.io.*;
import javax.swing.*; // Biblioteca do Swing para interfaces gráficas
import java.awt.event.*;

import javax.sound.midi.*;

// https://devoloper.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//http://www.music-software-devolopment.com/midi-tutorial.html
public class Player{

	public static int j;
	public static void main(String[] args) {
		//new Player(args[0]);
		try{
			Synthesizer syn = MidiSystem.getSynthesizer();
			Sequencer seq = MidiSystem.getSequencer();
			syn.open();
			Soundbank soundBank = syn.getDefaultSoundbank();
			MidiChannel[] mc = syn.getChannels();

			Instrument[] instr = soundBank.getInstruments();
			// Instrument[] instr = syn.getAvailableInstruments();

			// for(int i=0; i<instr.length; i++){
			// 	System.out.print(i);
			// 	System.out.println(instr[i]);
			// }
			syn.loadAllInstruments(soundBank);
			

			//syn.loadInstrument(instr[73]);	

			JFrame frame = new JFrame("Sound1");                
			JPanel pane = new JPanel();
			frame.getContentPane().add(pane); 
			JButton[] buttons = new JButton[128];

			for(j=0;j<1;j++){
			String jota = Integer.toString(j);
			buttons[j] = new JButton(jota);            
			                  
			pane.add(buttons[j]);                                  
			/* Troca o instrumento do canal */   
			mc[7].programChange(instr[1].getPatch().getProgram());
			
			buttons[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {  
					try{
						// Para aumentar o bmp diminui-se o tempo entre notas
						// e para diminuir aumenta-se o tempo. 
						int tempo = 500; // ms

						int vol = 200; // Volume da nota
			
						int oitava = 12;
						int c4 = 60; // dó
						int d4 = 62; // ré
						int e4 = 64; // mi
						int f4 = 65; // fa
						int g4 = 67; // sol
						int a4 = 69; // la
						int b4 = 71; // si

						int c5 = c4 + oitava;
						int d5 = d4 + oitava;
						int e5 = e4 + oitava;
						int f5 = f4 + oitava;
						int g5 = g4 + oitava;
						int a5 = a4 + oitava;
						int b5 = b4 + oitava;
						//*
						mc[7].noteOn(c5,vol);// Dó // Nota, Volume
					 	Thread.sleep(tempo);
					  	mc[7].noteOn(d5,vol); // Ré
						Thread.sleep(tempo);
					 	mc[7].noteOn(e5,vol); // Mi
					 	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fá

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fá
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fá

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(c5,vol);// Dó
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(d5,vol); // Ré
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(c5,vol);// Dó
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(d5,vol); // Ré

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(d5,vol);// Ré
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(d5,vol); // Ré

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(c5,vol); // Dó
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(g5,vol); // Sol
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fa
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(e5,vol);  // Mi

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(e5,vol); // Mi
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(e5,vol); // Mi

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(c5,vol);// Dó 
					 	Thread.sleep(tempo);
					  	mc[7].noteOn(d5,vol); // Ré
						Thread.sleep(tempo);
					 	mc[7].noteOn(e5,vol); // Mi
					 	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fá

					  	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fá
					  	Thread.sleep(tempo);
					  	mc[7].noteOn(f5,vol); // Fá
						//*/
					  	int batidas = 24;
					  	float tempoS = (float) tempo/1000;
					  	float bpm = batidas*60/((batidas-1)*tempoS);
					  	System.out.println(bpm);
					}
					 catch(Exception err){}
					
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