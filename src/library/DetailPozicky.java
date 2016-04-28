package library;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JScrollPane;

import database.DBPripojenie;

public class DetailPozicky {

	private JFrame frame;
	private String string;
	private DBPripojenie pripojenie;
	public static DefaultListModel pozicaneKnihy = new DefaultListModel();
	public static JList<Object> zoznamKnih;
	public static JScrollPane detailScroll;

	/**
	 * Create the application.
	 */
	public DetailPozicky(String string, DBPripojenie pripojenie) {
		this.string=string;
		this.pripojenie = pripojenie;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 640, 389);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		
		//detail pozicanych
		DBPripojenie.zobrazDetail(string, pripojenie);
		detailScroll = new JScrollPane(zoznamKnih);
		
		detailScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(detailScroll);
		frame.validate();
		frame.repaint();
		detailScroll.setBounds(26, 22, 498, 273);
	}
}
