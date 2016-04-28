package library;

import java.awt.EventQueue;


import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import database.DBPripojenie;

public class FilterZanrov {

	private JFrame frame;
	public static JScrollPane scrollPane;
	public static JList<Object> zoznamKnih;
	public static DefaultListModel pozicaneKnihy = new DefaultListModel();


	/**
	 * Create the application.
	 */
	public FilterZanrov() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 520, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
				
		
		JCheckBox povest = new JCheckBox("poves\u0165");
		povest.setBounds(354, 10, 200, 50);
		frame.getContentPane().add(povest);
		
		povest.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				DBPripojenie.filtrujZanre("povesù");
			}
		});
		
		JCheckBox rozpravka = new JCheckBox("rozpr\u00E1vka");
		rozpravka.setBounds(354, 52, 200, 50);
		frame.getContentPane().add(rozpravka);
		
		rozpravka.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				DBPripojenie.filtrujZanre("rozpr·vka");
			}
		});
		
		JCheckBox roman = new JCheckBox("rom\u00E1n");
		roman.setBounds(354, 105, 200, 50);
		frame.getContentPane().add(roman);
		
		roman.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				DBPripojenie.filtrujZanre("rom·n");
			}
		});
		
		JCheckBox poviedka = new JCheckBox("poviedka");
		poviedka.setBounds(354, 158, 200, 50);
		frame.getContentPane().add(poviedka);
		
		JButton btnZobraz = new JButton("Zobraz");
		btnZobraz.setBounds(354, 215, 89, 23);
		frame.getContentPane().add(btnZobraz);
		
		btnZobraz.addActionListener(new ActionListener(){
			
			// prepne sa na okno pridanie novej knihy
			public void actionPerformed(ActionEvent e) {
				DBPripojenie.filtrujZanre("");
				scrollPane = new JScrollPane(zoznamKnih);
				scrollPane.setBounds(10, 10, 322, 225);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				frame.getContentPane().add(scrollPane);
				frame.validate();
				frame.repaint();
			}
		});
		
		poviedka.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				DBPripojenie.filtrujZanre("poviedka");
				
			}
		});
		
		
		
	}
}
