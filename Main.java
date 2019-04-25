import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Main{

	private static boolean exit = false;

	public static void main(String[] args) {

		Scanner myInput = new Scanner(System.in);
		Configuracoes config = new Configuracoes();
		Conversor conversor = new Conversor();
		

		System.out.print("Instrumento: ");
		System.out.println(config.getInstrumento());
		System.out.print("BPM: ");
		System.out.println(config.getBPM());

		config.setInstrumento("violino");
		config.setBPM(742);

		System.out.print("Instrumento: ");
		System.out.println(config.getInstrumento());
		System.out.print("BPM: ");
		System.out.println(config.getBPM());

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
