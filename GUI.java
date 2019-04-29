import javax.swing.*;  

public class GUI{

	private static final int WINDOW_WIDTH = 992;
	private static final int WINDOW_HEIGHT = 600;

	private static final int MARGIN = 30;
	private static final int RESPIRO = 15;

	private static final int ALL_X = MARGIN;
	private static final int LABELS_HEIGHT = 24;
	private static final int ALL_WIDTH = WINDOW_WIDTH - 2*MARGIN;

	private static final int TEXTLABEL_Y = MARGIN;

	private static final int CAIXATEXT_Y = TEXTLABEL_Y + LABELS_HEIGHT;
	private static final int CAIXATEXT_HEIGHT = 300;

	private static final int BPMLABEL_Y = CAIXATEXT_Y + CAIXATEXT_HEIGHT + RESPIRO;

	private static final int BPMTEXTO_Y = BPMLABEL_Y + LABELS_HEIGHT;
	private static final int BPMTEXTO_HEIGHT = 24;

	private static final int INSTRUMENTOLABEL_Y = BPMTEXTO_Y + BPMTEXTO_HEIGHT + RESPIRO;

	private static final int INSTRUMENTOSBOX_Y = INSTRUMENTOLABEL_Y + LABELS_HEIGHT;
	private static final int INSTRUMENTOSBOX_HEIGHT = 24;

	private static final int TOCARMUSICA_WIDTH = 150;
	private static final int TOCARMUSICA_X = WINDOW_WIDTH - MARGIN - TOCARMUSICA_WIDTH;
	private static final int TOCARMUSICA_Y = INSTRUMENTOSBOX_Y + INSTRUMENTOSBOX_HEIGHT + RESPIRO;
	private static final int TOCARMUSICA_HEIGHT = 24;

	public static void main(String[] args) {
		JFrame frame=new JFrame("TextToMusic O'Tron9000"); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel textoLabel = new JLabel("Insira sua música aqui:");
		textoLabel.setBounds(ALL_X, TEXTLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		JTextArea caixaTexto = new JTextArea();
		caixaTexto.setBounds(ALL_X, CAIXATEXT_Y, ALL_WIDTH, CAIXATEXT_HEIGHT);
		
		JLabel bpmLabel = new JLabel("BPM (valor inteiro):");
		bpmLabel.setBounds(ALL_X, BPMLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		JTextField bpmTexto = new JTextField();
		bpmTexto.setBounds(ALL_X, BPMTEXTO_Y, ALL_WIDTH, BPMTEXTO_HEIGHT);

		JLabel instrumentoLabel = new JLabel("Selecione seu instrumento:");
		instrumentoLabel.setBounds(ALL_X, INSTRUMENTOLABEL_Y, ALL_WIDTH, LABELS_HEIGHT);
		String instrumentos[] = {"Piano","Violão","Baixo","Xilofone"};        
    	JComboBox<String> instrumentosBox = new JComboBox<String>(instrumentos);    
    	instrumentosBox.setBounds(ALL_X, INSTRUMENTOSBOX_Y, ALL_WIDTH, INSTRUMENTOSBOX_HEIGHT); 

		JButton tocarMusica=new JButton("Tocar Música!");  
		tocarMusica.setBounds(TOCARMUSICA_X, TOCARMUSICA_Y, TOCARMUSICA_WIDTH, TOCARMUSICA_HEIGHT);//x axis, y axis, width, height

		frame.add(textoLabel);
		frame.add(caixaTexto);
		frame.add(bpmLabel);
		frame.add(bpmTexto);
		frame.add(instrumentoLabel);
		frame.add(instrumentosBox);
		frame.add(tocarMusica);


		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);//400 width and 500 height  
		frame.setLayout(null);//using no layout managers  
		frame.setVisible(true);//making the frame visible  
	}  
}