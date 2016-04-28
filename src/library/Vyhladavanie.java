package library;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

public class Vyhladavanie {

	private JFrame frame;
	private JTextField textFieldVyhladaj;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vyhladavanie window = new Vyhladavanie();
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
	public Vyhladavanie() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 603, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblVyhladvanie = new JLabel("Vyhlad\u00E1vanie:");
		lblVyhladvanie.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblVyhladvanie.setBounds(26, 21, 140, 30);
		frame.getContentPane().add(lblVyhladvanie);
		
		textFieldVyhladaj = new JTextField();
		textFieldVyhladaj.setBounds(176, 21, 187, 27);
		frame.getContentPane().add(textFieldVyhladaj);
		textFieldVyhladaj.setColumns(10);
		
		JCheckBox chNazovKnihy = new JCheckBox("N\u00E1zov knihy");
		chNazovKnihy.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		chNazovKnihy.setBounds(26, 73, 97, 23);
		frame.getContentPane().add(chNazovKnihy);
		
		JCheckBox chAutor = new JCheckBox("Autor");
		chAutor.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		chAutor.setBounds(146, 73, 97, 23);
		frame.getContentPane().add(chAutor);
		
		JCheckBox chckbxner = new JCheckBox("\u017D\u00E1ner");
		chckbxner.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		chckbxner.setBounds(252, 74, 97, 23);
		frame.getContentPane().add(chckbxner);
		
		JCheckBox chckbxVydavatelstvo = new JCheckBox("Vydavatelstvo");
		chckbxVydavatelstvo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		chckbxVydavatelstvo.setBounds(351, 74, 116, 23);
		frame.getContentPane().add(chckbxVydavatelstvo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 110, 541, 239);
		frame.getContentPane().add(scrollPane);
	}
}
