package library;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;






import database.DBPripojenie;




import javax.swing.JList;
import javax.swing.JComboBox;

public class HlavneOkno {

	private JFrame frmKniznica;
	public static DefaultListModel zoznamKnih = new DefaultListModel();
	public static JList<Object> zoznam;
	public static JScrollPane KnihyscrollPane;
	public static ListSelectionModel listSelectionModel;
	private DBPripojenie pripojenie;


	/**
	 * Create the application.
	 * @param pripojenie 
	 */
	public HlavneOkno(DBPripojenie pripojenie) {
		this.pripojenie = pripojenie;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKniznica = new JFrame();
		frmKniznica.setVisible(true);
		frmKniznica.setTitle("KNI\u017DNICA");
		frmKniznica.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmKniznica.setBounds(100, 100, 639, 517);
		frmKniznica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKniznica.getContentPane().setLayout(null);
		
		JButton btnNewPridajKnihu = new JButton("Pridaj knihu");
		btnNewPridajKnihu.setForeground(Color.BLUE);
		btnNewPridajKnihu.setBackground(Color.GRAY);
		btnNewPridajKnihu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewPridajKnihu.addActionListener(new ActionListener(){
			
			// prepne sa na okno pridanie novej knihy
			public void actionPerformed(ActionEvent e) {
				new PridajKnihu(pripojenie);
			}
		});
		btnNewPridajKnihu.setBounds(10, 384, 163, 27);
		frmKniznica.getContentPane().add(btnNewPridajKnihu);
		
		JButton btnPozicane = new JButton("Po\u017Ei\u010Dan\u00E9 knihy");
		btnPozicane.addActionListener(new ActionListener() {
			
			// prepne sa na okno pozicane knihy
			public void actionPerformed(ActionEvent e) {
				new PozicaneKnihy();
			}
		});
		btnPozicane.setBackground(Color.GRAY);
		btnPozicane.setForeground(Color.BLUE);
		btnPozicane.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnPozicane.setBounds(230, 429, 163, 27);
		frmKniznica.getContentPane().add(btnPozicane);
		
		JButton btnAktualizujKnihu = new JButton("Aktualizuj knihu");
		btnAktualizujKnihu.addActionListener(new ActionListener() {
			
			// prepne sa na okno aktualizuj knihu
			public void actionPerformed(ActionEvent e) {
				new AktualizujKnihu(zoznam.getSelectedValue().toString(), pripojenie);
				
			}
		});
		btnAktualizujKnihu.setForeground(Color.BLUE);
		btnAktualizujKnihu.setBackground(Color.GRAY);
		btnAktualizujKnihu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnAktualizujKnihu.setBounds(10, 429, 163, 27);
		frmKniznica.getContentPane().add(btnAktualizujKnihu);
		
		JButton btnVymazKnihu = new JButton("Vyma\u017E knihu");
		btnVymazKnihu.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				DBPripojenie.vymazKnihu(zoznam.getSelectedValue().toString());
				
				
			}
		});
		btnVymazKnihu.setForeground(Color.BLUE);
		btnVymazKnihu.setBackground(Color.GRAY);
		btnVymazKnihu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnVymazKnihu.setBounds(230, 384, 163, 27);
		frmKniznica.getContentPane().add(btnVymazKnihu);
		
		JButton btnDetail = new JButton("Detail v\u00FDpo\u017Ei\u010Dky");
		btnDetail.setBackground(Color.GRAY);
		btnDetail.setForeground(Color.BLUE);
		btnDetail.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnDetail.setBounds(432, 384, 155, 27);
		frmKniznica.getContentPane().add(btnDetail);
        btnDetail.addActionListener(new ActionListener() {
			
			// prepne sa na okno detail knihy
			public void actionPerformed(ActionEvent e) {
				new DetailPozicky(zoznam.getSelectedValue().toString(), pripojenie);	
			}
		});
		
		//zobrazenie databazy
		DBPripojenie.zobrazDatabazu();
		KnihyscrollPane = new JScrollPane(zoznam);
		KnihyscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frmKniznica.getContentPane().add(KnihyscrollPane);
		frmKniznica.validate();
		frmKniznica.repaint();
		KnihyscrollPane.setBounds(10, 45, 577, 328);
		
		JLabel lblPrehadKn = new JLabel("Preh\u013Ead kn\u00EDh v kni\u017Enici:");
		lblPrehadKn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblPrehadKn.setBounds(10, 0, 200, 50);
		frmKniznica.getContentPane().add(lblPrehadKn);
		
		JButton btnNewButton = new JButton("Filtruj \u017E\u00E1nre");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBounds(432, 429, 155, 27);
		frmKniznica.getContentPane().add(btnNewButton);
		  btnNewButton.addActionListener(new ActionListener() {
				
				// prepne sa na okno filter zanrov
				public void actionPerformed(ActionEvent e) {
					FilterZanrov.pozicaneKnihy.clear();
					new FilterZanrov();
					
				}
			});
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
	}
}
