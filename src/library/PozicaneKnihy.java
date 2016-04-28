package library;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;

import database.DBPripojenie;

public class PozicaneKnihy {

	private JFrame frame;
	public static DefaultListModel pozicaneKnihy = new DefaultListModel();
	public static JList<Object> zoznamKnih;
	public static JScrollPane pozScrollPane;

	/**
	 * Create the application.
	 */
	public PozicaneKnihy() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 619, 402);
		frame.getContentPane().setLayout(null);
		
		//vypis pozicanych
		DBPripojenie.zobrazPozicane();
		pozScrollPane = new JScrollPane(zoznamKnih);
		pozScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(pozScrollPane);
		frame.validate();
		frame.repaint();
		pozScrollPane.setBounds(26, 22, 541, 328);
		
	}
}
