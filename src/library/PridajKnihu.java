package library;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import database.DBPripojenie;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PridajKnihu {

	private JFrame frame;
	private JTextField NazovText;
	private JTextField AutorText;
	private JTextField ZanerText;
	private JTextField PocetText;
	private JTextField VydavatelstvoText;
	private JTextField RokText;
	private JTextField ISBNText;
	private DBPripojenie pripojenie;

	
	/**
	 * Create the application.
	 * 
	 * @param pripojenie
	 */
	public PridajKnihu(DBPripojenie pripojenie) {
		this.pripojenie = pripojenie;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 336, 415);
		frame.setVisible(true);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNazovKnihy = new JLabel("Nazov knihy:");
		lblNazovKnihy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNazovKnihy.setBounds(10, 11, 119, 50);
		frame.getContentPane().add(lblNazovKnihy);

		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblAutor.setBounds(10, 55, 200, 34);
		frame.getContentPane().add(lblAutor);

		JLabel lblVydavatelstvo = new JLabel("Vydavatelstvo:");
		lblVydavatelstvo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblVydavatelstvo.setBounds(10, 197, 200, 28);
		frame.getContentPane().add(lblVydavatelstvo);

		JLabel lblRokVydania = new JLabel("Rok vydania:");
		lblRokVydania.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblRokVydania.setBounds(10, 230, 200, 39);
		frame.getContentPane().add(lblRokVydania);

		JLabel lblPocet = new JLabel("Po\u010Det str\u00E1n:");
		lblPocet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblPocet.setBounds(10, 131, 200, 28);
		frame.getContentPane().add(lblPocet);

		JLabel lblZaner = new JLabel("\u017D\u00E1ner:");
		lblZaner.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblZaner.setBounds(10, 94, 200, 34);
		frame.getContentPane().add(lblZaner);

		NazovText = new JTextField();
		NazovText.setBounds(127, 28, 139, 20);
		frame.getContentPane().add(NazovText);
		NazovText.setColumns(10);

		AutorText = new JTextField();
		AutorText.setBounds(127, 64, 139, 20);
		frame.getContentPane().add(AutorText);
		AutorText.setColumns(10);

		ZanerText = new JTextField();
		ZanerText.setBounds(127, 100, 139, 20);
		frame.getContentPane().add(ZanerText);
		ZanerText.setColumns(10);

		PocetText = new JTextField();
		PocetText.setBounds(127, 137, 139, 20);
		frame.getContentPane().add(PocetText);
		PocetText.setColumns(10);

		VydavatelstvoText = new JTextField();
		VydavatelstvoText.setBounds(127, 203, 139, 20);
		frame.getContentPane().add(VydavatelstvoText);
		VydavatelstvoText.setColumns(10);

		RokText = new JTextField();
		RokText.setBounds(127, 236, 139, 20);
		frame.getContentPane().add(RokText);
		RokText.setColumns(10);

		JButton btnUloz = new JButton("Ulo\u017E");
		btnUloz.addActionListener(new ActionListener() {
			// pridanie zaznamu
			public void actionPerformed(ActionEvent arg0) {
				try {
					// Najprv vydavatelstvo
					pripojenie.getConnection().setAutoCommit(false);
					String sql = "SELECT * FROM vydavatelstva WHERE nazov = '"
							+ VydavatelstvoText.getText() + "' ";
					ResultSet rs = pripojenie.getState().executeQuery(sql);
					int vydavatelstvo_id;
					if (!rs.next()) {
						sql = "INSERT INTO vydavatelstva (nazov) VALUES ('"
								+ VydavatelstvoText.getText()
								+ "') RETURNING id";
						rs = pripojenie.getState().executeQuery(sql);
						rs.next();
						vydavatelstvo_id = rs.getInt("id");
					} else {
						vydavatelstvo_id = rs.getInt("id");
					}
					// 1.pridaj knihu a zober id
					sql = "INSERT INTO knihy (nazov,isbn, pocet_stran, rok, vydavatelstvo_id) VALUES ('"
							+ NazovText.getText()
							+ "', '"
							+ ISBNText.getText()
							+ "', '"
							+ PocetText.getText()
							+ "', '"
							+ RokText.getText()
							+ "', "
							+ vydavatelstvo_id
							+ ") RETURNING id";
					rs = pripojenie.getState().executeQuery(sql);
					rs.next();
					int kniha_id = rs.getInt("id"); // prave pridanej knihy
					//System.out.println("kniha_id: " + kniha_id);
					// 2.pridaj zaner ak neexistuje
					sql = "SELECT * FROM zanre WHERE nazov_zanru = '"
							+ ZanerText.getText() + "' ";
					rs = pripojenie.getState().executeQuery(sql);
					int zaner_id;

					if (!rs.next()) {
						// pridam knihu ked zaner neexistuje
						sql = "INSERT INTO zanre (nazov_zanru) VALUES ('"
								+ ZanerText.getText() + "') RETURNING id";
						rs = pripojenie.getState().executeQuery(sql);
						rs.next();
						zaner_id = rs.getInt("id");
					} else {
						zaner_id = rs.getInt("id");
					}
					//System.out.println("zaner_id: " + zaner_id);
					// 3.spoj zaner s knihou

					sql = "INSERT INTO mtm_zanre_knihy (knihy_id, zanre_id) VALUES((SELECT id from knihy where nazov = ?), (SELECT id from zanre where nazov_zanru = ?))";
					PreparedStatement stm = pripojenie.getConnection()
							.prepareStatement(sql);
					stm.setString(1, NazovText.getText());
					stm.setString(2, ZanerText.getText());
					stm.executeUpdate();
					//System.out.println("sql_knihy: " + sql);
					
					sql = "SELECT * FROM autori WHERE meno = '"
							+ AutorText.getText() + "' ";
					rs = pripojenie.getState().executeQuery(sql);
					int autor_id;
					if (!rs.next()) {
						sql = "INSERT INTO autori (meno) VALUES ('"
								+ AutorText.getText() + "') RETURNING id";
						rs = pripojenie.getState().executeQuery(sql);
						rs.next();
						autor_id = rs.getInt("id");
					} else {
						autor_id = rs.getInt("id");
					}
					//System.out.println("autor_id: " + autor_id);
					
					sql = "INSERT INTO mtm_autori_knihy (knihy_id, autori_id) VALUES((SELECT id from knihy where nazov = ?), (SELECT id from autori where meno = ?))";
					stm = pripojenie.getConnection().prepareStatement(sql);
					stm.setString(1, NazovText.getText());
					stm.setString(2, AutorText.getText());
					stm.executeUpdate();
					
					pripojenie.getConnection().setAutoCommit(true);
					//System.out.println("sql_autory: " + sql);
					JOptionPane.showMessageDialog(null, "Kniha bola pridan· :)");
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Treba vyplniù ˙daje!");
				}
				DBPripojenie.zobrazDatabazu();
				HlavneOkno.KnihyscrollPane = new JScrollPane(HlavneOkno.zoznam);
			}

		});
		btnUloz.setForeground(Color.BLUE);
		btnUloz.setBackground(Color.GRAY);
		btnUloz.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnUloz.setBounds(147, 323, 119, 27);
		frame.getContentPane().add(btnUloz);

		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblIsbn.setBounds(10, 158, 200, 34);
		frame.getContentPane().add(lblIsbn);

		ISBNText = new JTextField();
		ISBNText.setBounds(127, 170, 139, 20);
		frame.getContentPane().add(ISBNText);
		ISBNText.setColumns(10);
	}
}
