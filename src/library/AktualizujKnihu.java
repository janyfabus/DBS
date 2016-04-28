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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

public class AktualizujKnihu {
	private int knihaId;
	private int zanerId;
	private int autorId;
	private int vydavatelId;
	private JFrame frame;
	private JLabel labelNazov;
	private JLabel labelAutor;
	private JLabel labelZaner;
	private JLabel labelPocet;
	private JLabel labelISBN;
	private JLabel labelVydavatelstvo;
	private JLabel labelRok;
	private JButton buttonUloz;
	private JTextField NazovText;
	private JTextField AutorText;
	private JTextField ZanerText;
	private JTextField PocetText;
	private JTextField ISBNText;
	private JTextField VydavatelstvoText;
	private JTextField RokText;
	private String string;
	private DBPripojenie pripojenie;

	
	/**
	 * Create the application.
	 * @param string 
	 */
	public AktualizujKnihu(String string, DBPripojenie pripojenie) {
		this.string=string;
		this.pripojenie = pripojenie;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 333, 401);
		frame.getContentPane().setLayout(null);
		
		labelNazov = new JLabel("Nazov knihy:");
		labelNazov.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelNazov.setBounds(10, 10, 119, 50);
		frame.getContentPane().add(labelNazov);
		
		labelAutor = new JLabel("Autor:");
		labelAutor.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelAutor.setBounds(10, 50, 200, 34);
		frame.getContentPane().add(labelAutor);
		
		labelZaner = new JLabel("\u017D\u00E1ner:");
		labelZaner.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelZaner.setBounds(10, 81, 200, 34);
		frame.getContentPane().add(labelZaner);
		
		labelPocet = new JLabel("Po\u010Det:");
		labelPocet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelPocet.setBounds(10, 113, 200, 28);
		frame.getContentPane().add(labelPocet);
		
		labelISBN = new JLabel("ISBN:");
		labelISBN.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelISBN.setBounds(10, 152, 200, 34);
		frame.getContentPane().add(labelISBN);
		
		labelVydavatelstvo = new JLabel("Vydavatelstvo:");
		labelVydavatelstvo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelVydavatelstvo.setBounds(10, 191, 200, 28);
		frame.getContentPane().add(labelVydavatelstvo);
		
		labelRok = new JLabel("Rok vydania:");
		labelRok.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		labelRok.setBounds(10, 217, 200, 34);
		frame.getContentPane().add(labelRok);
		
		buttonUloz = new JButton("Aktualizuj");
		buttonUloz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String sql="UPDATE knihy SET nazov = '"+NazovText.getText()
						+ "' ,pocet_stran = "+PocetText.getText()
						+ ",rok = "+RokText.getText()
						+ ",isbn = '"+ISBNText.getText()+"' WHERE id="+knihaId;

				//System.out.println(sql);
				Savepoint savepoint1 = null;
				
				try {
					pripojenie.getConnection().setAutoCommit(false);
					pripojenie.getState().executeUpdate(sql);
					//AKTUALIZUJEM ZANER
					sql = "UPDATE zanre SET nazov_zanru = '"+ZanerText.getText()+"' WHERE id =" + zanerId;
					pripojenie.getState().executeUpdate(sql);
					//AKTUALIZUJEM VYDAVATELA
					sql = "UPDATE vydavatelstva SET nazov = '"+VydavatelstvoText.getText()+"' WHERE id =" + vydavatelId;
					pripojenie.getState().executeUpdate(sql);
					//AKTUALIZUJEM AUTORA
					sql = "UPDATE autori SET meno = '"+AutorText.getText()+"' WHERE id =" + autorId;
					pripojenie.getState().executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Aktualizovane :)");
					pripojenie.getConnection().setAutoCommit(true);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Nepodarilo sa aktualizovaù!");
					e.printStackTrace();
				}
				DBPripojenie.zobrazDatabazu();
				HlavneOkno.KnihyscrollPane = new JScrollPane(HlavneOkno.zoznam);
				
				
			}
		});
		buttonUloz.setForeground(Color.BLUE);
		buttonUloz.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		buttonUloz.setBackground(Color.GRAY);
		buttonUloz.setBounds(163, 296, 119, 27);
		frame.getContentPane().add(buttonUloz);
		
		NazovText = new JTextField();
		NazovText.setColumns(10);
		NazovText.setBounds(143, 27, 139, 20);
		frame.getContentPane().add(NazovText);
		
		AutorText = new JTextField();
		AutorText.setColumns(10);
		AutorText.setBounds(143, 59, 139, 20);
		frame.getContentPane().add(AutorText);
		
		ZanerText = new JTextField();
		ZanerText.setColumns(10);
		ZanerText.setBounds(143, 90, 139, 20);
		frame.getContentPane().add(ZanerText);
		
		PocetText = new JTextField();
		PocetText.setColumns(10);
		PocetText.setBounds(143, 119, 139, 20);
		frame.getContentPane().add(PocetText);
		
		ISBNText = new JTextField();
		ISBNText.setColumns(10);
		ISBNText.setBounds(143, 161, 139, 20);
		frame.getContentPane().add(ISBNText);
		
		VydavatelstvoText = new JTextField();
		VydavatelstvoText.setColumns(10);
		VydavatelstvoText.setBounds(143, 197, 139, 20);
		frame.getContentPane().add(VydavatelstvoText);
		
		RokText = new JTextField();
		RokText.setColumns(10);
		RokText.setBounds(143, 226, 139, 20);
		frame.getContentPane().add(RokText);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Fillfield();
	}
	
	public void Fillfield()
	{
		
		knihaId = DBPripojenie.Get_value(string);
		String sql = "SELECT * FROM prehlad_knih WHERE id = '"+knihaId+"'";
		try {
			//System.out.println(sql);
			ResultSet rs = pripojenie.getState().executeQuery(sql);
			rs.next();
			autorId = rs.getShort("autor_id");
			zanerId = rs.getShort("zaner_id");
			vydavatelId = rs.getShort("vydavatel_id");
			NazovText.setText(rs.getString("nazov"));
			AutorText.setText(rs.getString("meno"));
			ISBNText.setText(rs.getString("isbn"));
			ZanerText.setText(rs.getString("nazov_zanru"));
			PocetText.setText(rs.getString("pocet_stran"));
			VydavatelstvoText.setText(rs.getString("vydavatelstvo"));
			RokText.setText(rs.getString("rok"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
