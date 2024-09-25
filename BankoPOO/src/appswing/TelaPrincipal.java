package appswing;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranh�o Ayres
 **********************************/

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnConta;
	private JMenu mnCorrentista;
	private JLabel label;
	private JMenu mnCaixa;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("BANKO");
		frame.setBounds(100, 100, 450, 363);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("Inicializando...");
		label.setBounds(0, 0, 450, 313);
		ImageIcon imagem = new ImageIcon(getClass().getResource("/arquivos/Imagem.png"));
		imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));//		label.setIcon(fotos);
		label.setIcon(imagem);
		frame.getContentPane().add(label);
		frame.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mnConta = new JMenu("Conta");
		mnConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaContas tela = new TelaContas();
			}
		});
		menuBar.add(mnConta);

		mnCorrentista = new JMenu("Correntista");
		mnCorrentista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaCorrentistas tela = new TelaCorrentistas();
			}
		});
		menuBar.add(mnCorrentista);
		
		mnCaixa = new JMenu("Caixa");
		mnCaixa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaCaixa tela = new TelaCaixa();
			}
		});
		menuBar.add(mnCaixa);
	}
}
