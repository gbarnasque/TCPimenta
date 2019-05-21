import javax.swing.*;  
import java.awt.event.*;

public class GUI{

	private static final int WINDOW_WIDTH = 992;
	private static final int WINDOW_HEIGHT = 600;

	private static final int MARGIN = 30;
	private static final int RESPIRO = 15;

	private static final int ALL_X = MARGIN;
	private static final int LABELS_HEIGHT = 24;
	private static final int ALL_WIDTH = WINDOW_WIDTH - 2*MARGIN;

	private static final int MUSICALABEL_Y = MARGIN;

	private static final int MUSICATEXT_Y = MUSICALABEL_Y + LABELS_HEIGHT;
	private static final int MUSICATEXT_HEIGHT = 300;

	private static final int BPMLABEL_Y = MUSICATEXT_Y + MUSICATEXT_HEIGHT + RESPIRO;

	private static final int BPMTEXTO_Y = BPMLABEL_Y + LABELS_HEIGHT;
	private static final int BPMTEXTO_HEIGHT = 24;

	private static final int INSTRUMENTOLABEL_Y = BPMTEXTO_Y + BPMTEXTO_HEIGHT + RESPIRO;

	private static final int INSTRUMENTOSBOX_Y = INSTRUMENTOLABEL_Y + LABELS_HEIGHT;
	private static final int INSTRUMENTOSBOX_HEIGHT = 24;

	private static final int TOCARMUSICA_WIDTH = 150;
	private static final int TOCARMUSICA_X = WINDOW_WIDTH - MARGIN - TOCARMUSICA_WIDTH;
	private static final int TOCARMUSICA_Y = INSTRUMENTOSBOX_Y + INSTRUMENTOSBOX_HEIGHT + RESPIRO;
	private static final int TOCARMUSICA_HEIGHT = 24;

	public GUI(){
		JFrame frame=new JFrame("TextToMusic O'Tron9000"); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel musicaLabel = new JLabel("Insira sua mÃºsica aqui:");
		musicaLabel.setBounds(ALL_X, MUSICALABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		JTextArea musicaTexto = new JTextArea();
		musicaTexto.setBounds(ALL_X, MUSICATEXT_Y, ALL_WIDTH, MUSICATEXT_HEIGHT);
		
		JLabel bpmLabel = new JLabel("BPM (valor inteiro):");
		bpmLabel.setBounds(ALL_X, BPMLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		JTextField bpmTexto = new JTextField();
		bpmTexto.setBounds(ALL_X, BPMTEXTO_Y, ALL_WIDTH, BPMTEXTO_HEIGHT);

		JLabel instrumentoLabel = new JLabel("Selecione seu instrumento:");
		instrumentoLabel.setBounds(ALL_X, INSTRUMENTOLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		String instrumentos[] = {"Piano","ViolÃ£o","Baixo","Xilofone"};        
    	JComboBox<String> instrumentosBox = new JComboBox<String>(instrumentos);    
    	instrumentosBox.setBounds(ALL_X, INSTRUMENTOSBOX_Y, ALL_WIDTH, INSTRUMENTOSBOX_HEIGHT); 

		JButton tocarMusica=new JButton("Tocar MÃºsica!");  
		tocarMusica.setBounds(TOCARMUSICA_X, TOCARMUSICA_Y, TOCARMUSICA_WIDTH, TOCARMUSICA_HEIGHT);//x axis, y axis, width, height

		frame.add(musicaLabel);
		frame.add(musicaTexto);
		frame.add(bpmLabel);
		frame.add(bpmTexto);
		frame.add(instrumentoLabel);
		frame.add(instrumentosBox);
		frame.add(tocarMusica);

		tocarMusica.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String musica = musicaTexto.getText();
				if(musica.equals("")){
					JOptionPane.showMessageDialog(frame, "Entrada de mÃºsica vazia");
				}
				else{
					try{
						int bpm = Integer.parseInt(bpmTexto.getText());
						if(bpm < 1){
							JOptionPane.showMessageDialog(frame, "BPM inserido nÃ£o Ã© um inteiro vÃ¡lido");
						}
						else{
							String instrumento = instrumentosBox.getSelectedItem().toString();
							Player player = new Player(musica, bpm, instrumento);
							player.playMusic();
						}
					}
					catch(NumberFormatException err){
						JOptionPane.showMessageDialog(frame, "BPM inserido nÃ£o Ã© um inteiro vÃ¡lido");
					}
				}
			}
		});

		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frame.setLayout(null);
		frame.setVisible(true);
	}  
}
