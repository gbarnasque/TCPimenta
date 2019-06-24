import javax.swing.*;  
import java.awt.event.*;
import java.io.File; 
import java.util.Scanner;

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

	private static final int BAIXARMUSICA_WIDTH = 150;
	private static final int BAIXARMUSICA_X = WINDOW_WIDTH - MARGIN - 2*TOCARMUSICA_WIDTH - RESPIRO;
	private static final int BAIXARMUSICA_Y = TOCARMUSICA_Y;
	private static final int BAIXARMUSICA_HEIGHT = 24;

	private static final int AJUDA_WIDTH = 100;
	private static final int AJUDA_X = ALL_X;
	private static final int AJUDA_Y = TOCARMUSICA_Y;
	private static final int AJUDA_HEIGHT = 24;

	private static final int ESCOLHERARQUIVO_WIDTH = 150;
	private static final int ESCOLHERARQUIVO_X =  WINDOW_WIDTH - MARGIN - 2*TOCARMUSICA_WIDTH - BAIXARMUSICA_WIDTH - 2*RESPIRO;
	private static final int ESCOLHERARQUIVO_Y = TOCARMUSICA_Y;
	private static final int ESCOLHERARQUIVO_HEIGHT = 24;	


	public GUI(){
		JFrame frame=new JFrame("TextToMusic O'Tron9000 V2"); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        
		JLabel musicaLabel = new JLabel("Insira sua música aqui:");
		musicaLabel.setBounds(ALL_X, MUSICALABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		JTextArea musicaTexto = new JTextArea();
		musicaTexto.setEditable(true);  
		musicaTexto.setLineWrap(true); 
		JScrollPane scroll = new JScrollPane(musicaTexto);
		scroll.setBounds(ALL_X, MUSICATEXT_Y, ALL_WIDTH, MUSICATEXT_HEIGHT);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


		JLabel bpmLabel = new JLabel("BPM (valor inteiro):");
		bpmLabel.setBounds(ALL_X, BPMLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		JTextField bpmTexto = new JTextField();
		bpmTexto.setBounds(ALL_X, BPMTEXTO_Y, ALL_WIDTH, BPMTEXTO_HEIGHT);

		JLabel instrumentoLabel = new JLabel("Selecione seu instrumento:");
		instrumentoLabel.setBounds(ALL_X, INSTRUMENTOLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		String instrumentos[] = {"Piano","Violão","Baixo","Xilofone","Harpsichord","Tubular Bells","Pan Flute","Church Organ"};        
    	JComboBox<String> instrumentosBox = new JComboBox<String>(instrumentos);    
    	instrumentosBox.setBounds(ALL_X, INSTRUMENTOSBOX_Y, ALL_WIDTH, INSTRUMENTOSBOX_HEIGHT); 

		JButton tocarMusica = new JButton("Tocar Música!");  
		tocarMusica.setBounds(TOCARMUSICA_X, TOCARMUSICA_Y, TOCARMUSICA_WIDTH, TOCARMUSICA_HEIGHT);

		JButton baixarMusica = new JButton("Baixar Música!");  
		baixarMusica.setBounds(BAIXARMUSICA_X, BAIXARMUSICA_Y, BAIXARMUSICA_WIDTH, BAIXARMUSICA_HEIGHT);

		JButton ajuda = new JButton("Ajuda");
		ajuda.setBounds(AJUDA_X, AJUDA_Y, AJUDA_WIDTH, AJUDA_HEIGHT);

		JButton escolherArquivo = new JButton("Escolher texto!");  
		escolherArquivo.setBounds(ESCOLHERARQUIVO_X, ESCOLHERARQUIVO_Y, ESCOLHERARQUIVO_WIDTH, ESCOLHERARQUIVO_HEIGHT);


		frame.add(musicaLabel);
		frame.add(scroll);
		frame.add(bpmLabel);
		frame.add(bpmTexto);
		frame.add(instrumentoLabel);
		frame.add(instrumentosBox);
		frame.add(tocarMusica);
		frame.add(baixarMusica);
		frame.add(ajuda);
		frame.add(escolherArquivo);

		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frame.setLayout(null);
		frame.setVisible(true);


		ajuda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String mensagemDeAjuda = "Para utilizar o programa você pode escrever seu texto diretamente na caixa de texto\n ou escolher um arquivo contendo seu texto ao clicar em \"Escolher arquivo!\".\n";
				JOptionPane.showMessageDialog(frame, mensagemDeAjuda);
			}
		});


		tocarMusica.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String musica = musicaTexto.getText();
				if(musica.equals("")){
					JOptionPane.showMessageDialog(frame, "Entrada de música vazia");
				}
				else{
					try{
						int bpm = Integer.parseInt(bpmTexto.getText());
						if(bpm < 1){
							JOptionPane.showMessageDialog(frame, "BPM inserido não é um inteiro válido (Maior que 0)");
						}
						else{
							String instrumento = instrumentosBox.getSelectedItem().toString();
							//Player player = new Player(musica, bpm, instrumento);
							//player.playMusic();
							PlayerV2 player = new PlayerV2();
						}
					}
					catch(NumberFormatException err){
						JOptionPane.showMessageDialog(frame, "BPM inserido não é um inteiro válido (Maior que 0)");
					}
				}
			}
		});

		baixarMusica.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String musica = musicaTexto.getText();
				if(musica.equals("")){
					JOptionPane.showMessageDialog(frame, "Entrada de música vazia");
				}
				else{
					try{
						int bpm = Integer.parseInt(bpmTexto.getText());
						if(bpm < 1){
							JOptionPane.showMessageDialog(frame, "BPM inserido não é um inteiro válido (Maior que 0)");
						}
						else{
							String instrumento = instrumentosBox.getSelectedItem().toString();
							Player player = new Player(musica, bpm, instrumento);
							//player.baixaMusica();
						}
					}
					catch(NumberFormatException err){
						JOptionPane.showMessageDialog(frame, "BPM inserido não é um inteiro válido (Maior que 0)");
					}
				}
			}
		});

		escolherArquivo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChooser = new JFileChooser();
				int isOk = fileChooser.showOpenDialog(null);

				if(isOk == JFileChooser.APPROVE_OPTION){
					try{
						File file = fileChooser.getSelectedFile();
						Scanner scanner = new Scanner(file);
						while(scanner.hasNextLine()){
							musicaTexto.append(scanner.nextLine());
							musicaTexto.append("\n");
						}
					}
					catch(Exception exc){
						exc.printStackTrace();
					}

				}
			}
		});
	}  
}
