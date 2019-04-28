import java.util.*;
import java.io.*;
import javax.swing.*; // Biblioteca do Swing para interfaces gráficas
import java.awt.event.*;
import javax.sound.sampled.*;

public class Main{

	private static boolean exit = false;

	AudioFormat format;
	AudioInputStream inputStream;
	SourceDataLine line;

	public static void main(String[] args) {
		new Player();
	}

	public Player(){
		try{
			File file = new File("1-welcome.wav");
			inputStream = AudioSystem.getAudioInputStream(file);
			format = inputStream.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			new PlayThread().start();

		}
		catch(Exception e){

		}
	

		//System.out.println(getMixerInfo());
		//Configuracoes config = new Configuracoes();
		//Conversor conversor = new Conversor();
		
		// System.out.print("Instrumento: ");
		// System.out.println(config.getInstrumento());
		// System.out.print("BPM: ");
		// System.out.println(config.getBPM());

		// config.setInstrumento("violino");
		// config.setBPM(742);

		// System.out.print("Instrumento: ");
		// System.out.println(config.getInstrumento());
		// System.out.print("BPM: ");
		// System.out.println(config.getBPM());

		// JFrame f = new JFrame();//creating instance of JFrame 
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// final JTextField tf=new JTextField();  
		// tf.setBounds(50,50,100,30); //xaxis, yaxis, width, height
		// JButton b=new JButton("Click Here");  
		// b.setBounds(50,80,150,30);  //xaxis, yaxis, width, height
		// b.addActionListener(new ActionListener(){  
		// 	public void actionPerformed(ActionEvent e){  
		//         tf.setText("Welcome to Javatpoint.");
		//         exit = true;
		//     }  
		// });  
		// f.add(b);
		// f.add(tf);  
		// f.setSize(800,800);  
		// f.setLayout(null);  
		// f.setVisible(true);

	}  

	public static void checkExit(){
		if(exit){
			System.exit(0);
		}
	}

		// var t = new Teste();
		// t.setVarteste1(myInput.nextInt());
		// t.setVarteste2(myInput.nextInt());
		// System.out.print(t.getVarteste1() + " multiplicado por " + t.getVarteste2() + " eh igual a ");
		// System.out.println(t.getMultiplicacao());
}

class PlayThread extends Thread{
	byte tempBuffer[] = new byte[10000];

	public void run(){
		try{
			line.open(format);
		}
		catch(Exception e){

		}
	}
}
