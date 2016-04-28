package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import library.DetailPozicky;
import library.FilterZanrov;
import library.HlavneOkno;
import library.PozicaneKnihy;



public class DBPripojenie {
	

public final static String DRIVER_CLASS		= "org.postgresql.Driver";
	
	private final static String USER 			= "postgres";
	private final static String PASS 			= "dbs2016";
	private final static String URL				= "localhost:5433/";
	private final static String DB				= "ProjektDBS";
	private final static String PREFIX			= "jdbc:postgresql://";
	
	private static Connection conn;
	private static Statement  state;
	
	private final String user;
	private final String url;
	private final String db;
	private final String pass;
	
	static DBPripojenie pripojenie;
	
	public DBPripojenie(){
		this(URL, DB, USER, PASS);
	}
	
	public DBPripojenie(String url, String db, String user, String pass){
		this.user 	= user;
		this.pass 	= pass;
		this.url 	= url;
		this.db 	= db;
		
		conn  = getConnection();
		state = getStatement(conn);
		
		
		//showDatabaseSize("ProjektDBS");
		
	}
	
	private Statement getStatement(Connection connection){
		if(connection == null){
			System.out.println("Connection je NULL");
			return null;
		}
			
		try {
			return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println("Nepodarilo sa zÌskaù Statement");
		}
		return null;
	}
	
	public  void getPreparedStatement(Connection con, String sql){
		try {
			con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		try {
			Class.forName(DRIVER_CLASS);
			System.out.println(PREFIX + url + db);
			return DriverManager.getConnection(PREFIX + url + db, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Nepodarilo sa zÌskaù Connection: " + e);
		}
		return null;
	}
	
	/*protected final void showDatabaseSize(String databaseName){
		try {
			ResultSet rs = state.executeQuery("SELECT pg_size_pretty(pg_database_size('" + databaseName + "'))");
			while(rs.next())
				System.out.println(rs.getString(1));
		} catch (SQLException e) {
			System.out.println("Nepodarilo sa zistiù veækosù datab·zy: " + databaseName);
		}
	}*/
	
	
	
	public static void zobrazDatabazu(){
		//String sql = "SELECT * FROM knihy k JOIN vydavatelstva v ON v.id = k.vydavatelstvo_id JOIN mtm_autori_knihy m ON m.knihy_id = k.id JOIN autori a ON a.id = m.autori_id JOIN mtm_zanre_knihy mz ON mz.knihy_id = k.id JOIN zanre z ON z.id = mz.zanre_id";
		String sql = "SELECT * FROM prehlad_knih";
		try {
			ResultSet rs = state.executeQuery(sql);
			HlavneOkno.zoznamKnih.clear();
			while(rs.next()) {
			    int id = rs.getInt("id");
			    String nazov_knihy = rs.getString("nazov");
			    String isbn = rs.getString("isbn");
			    int rok = rs.getInt("rok");
			    int pocet_stran = rs.getInt("pocet_stran");
			    String vydavatelstvo = rs.getString("vydavatelstvo");
			    String meno_autora = rs.getString("meno");
			    String nazov_zanru = rs.getString("nazov_zanru");
			    
			    /*System.out.println(rs.getInt("id"));
			    System.out.println(rs.getString("nazov"));
			    System.out.println(rs.getString("isbn"));
			    System.out.println(rs.getInt("rok"));
			    System.out.println(rs.getInt("pocet_stran"));
			    System.out.println(rs.getString("vydavatelstvo"));
			    System.out.println(rs.getString("meno"));
			    System.out.println(rs.getString("nazov_zanru"));*/
			    
			    HlavneOkno.zoznamKnih.addElement(id + " " + nazov_knihy + " " + isbn + " " + rok + " " + pocet_stran + " " + vydavatelstvo + " " + meno_autora + " " + nazov_zanru + " ");
			   
			    }
			    HlavneOkno.zoznam = new JList<Object>(HlavneOkno.zoznamKnih);
			    HlavneOkno.zoznam.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			    HlavneOkno.zoznam.setSelectedIndex(0);
			    HlavneOkno.zoznam.setVisibleRowCount(3); 
			    HlavneOkno.zoznam.setVisible(true);
			    //System.out.println(HlavneOkno.zoznamKnih);  
		} catch (SQLException e) {
			System.out.println("Nastala chyba: " + e);
		}
	}
	
	
	public static int Get_value(String s){
		int i = 0;
		int index = 0;
		int foreinKey = 0;
		for (i=0; i<1; i++){
			index = s.indexOf(' ', index + 1);
		}
		foreinKey = Integer.parseInt(s.substring(0, index));
		return foreinKey;
	}
	
	public static Statement getState() {
		return state;
	}

	//metoda na vymazanie zaznamu z JListu
	public static void vymazZaznam(int i){
		String sql = "DELETE * FROM knihy k JOIN vydavatelstva v ON v.id = k.vydavatelstvo_id JOIN mtm_autori_knihy m ON m.knihy_id = k.id JOIN autori a ON a.id = m.autori_id JOIN mtm_zanre_knihy mz ON mz.knihy_id = k.id JOIN zanre z ON z.id = mz.zanre_id WHERE k.id =" + i;
		try {
			state.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Nepodarilo sa vymazat");
		}
		
	}
	
	public static void zobrazPozicane(){
		String sql = "SELECT * FROM pozicane_knihy";
		try {
			ResultSet rs = state.executeQuery(sql);
			PozicaneKnihy.pozicaneKnihy.clear();
			while(rs.next()) {
				String meno = rs.getString("meno");
				String knihy = rs.getString("knihy");
				int pocet = rs.getInt("pocet");
			
				
				PozicaneKnihy.pozicaneKnihy.addElement(meno + " " + knihy + " " + pocet + " " );
			}
				
				PozicaneKnihy.zoznamKnih = new JList<Object>(PozicaneKnihy.pozicaneKnihy);
				PozicaneKnihy.zoznamKnih.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				PozicaneKnihy.zoznamKnih.setSelectedIndex(0);
				PozicaneKnihy.zoznamKnih.setVisibleRowCount(3); 
				PozicaneKnihy.zoznamKnih.setVisible(true);
				//System.out.println(PozicaneKnihy.pozicaneKnihy);
				
			
		} catch (SQLException e) {
			System.out.println("Nepodarilo sa vymazat");
		}
		
	}
		
	
	public static void zobrazDetail(String string, DBPripojenie pripojenie){
		int knihaId = DBPripojenie.Get_value(string);
		String sql = "SELECT z.meno, v.odkedy, v.dokedy, v.id, kn.id FROM zakaznici z "
				+ "JOIN vypozicka v ON z.id = v.zakaznici_id "
				+ "JOIN knihy_vo_vypozicke k ON k.vypozicka_id = v.id "
				+ "JOIN exemplare e ON e.id = k.exemplare_id "
				+ "JOIN knihy kn ON kn.id = e.knihy_id "
				+ "WHERE kn.id =" +knihaId;
		try {
			ResultSet rs = state.executeQuery(sql);
			DetailPozicky.pozicaneKnihy.clear();
			while(rs.next()) {
				String meno = rs.getString("meno");
				String odkedy = rs.getString("odkedy");
				String dokedy = rs.getString("dokedy");
				
				DetailPozicky.pozicaneKnihy.addElement(meno + " " + odkedy + " " + dokedy + " " );
			}
				
				DetailPozicky.zoznamKnih = new JList<Object>(DetailPozicky.pozicaneKnihy);
				DetailPozicky.zoznamKnih.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				DetailPozicky.zoznamKnih.setSelectedIndex(0);
				DetailPozicky.zoznamKnih.setVisibleRowCount(3); 
				DetailPozicky.zoznamKnih.setVisible(true);
				//System.out.println(DetailPozicky.pozicaneKnihy);
				
			
		} catch (SQLException e) {
			System.out.println("Nepodarilo sa vymazat");
		}
		
	}
	
	public static void filtrujZanre(String string){
		String sql = "SELECT k.nazov, z.nazov_zanru, k.id, z.id AS zaner_id FROM knihy k "
				+ "JOIN mtm_zanre_knihy mz ON mz.knihy_id = k.id "
				+ "JOIN zanre z ON z.id = mz.zanre_id "
				+ "WHERE z.nazov_zanru = '"+string+"'";  
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String nazov = rs.getString("nazov");
				
				FilterZanrov.pozicaneKnihy.addElement(nazov);
			}
				
				FilterZanrov.zoznamKnih = new JList<Object>(FilterZanrov.pozicaneKnihy);
				FilterZanrov.zoznamKnih.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				FilterZanrov.zoznamKnih.setSelectedIndex(0);
				FilterZanrov.zoznamKnih.setVisibleRowCount(3); 
				FilterZanrov.zoznamKnih.setVisible(true);
				//System.out.println(FilterZanrov.pozicaneKnihy);
				
			
		} catch (SQLException e) {
			System.out.println("Nepodarilo sa vymazat");
		}
		
	}
	
	
	public static void vymazKnihu(String string){
		int knihaId = DBPripojenie.Get_value(string);
		String sql1 = "DELETE FROM mtm_zanre_knihy WHERE knihy_id ='"+knihaId+"'";
		String sql2 = "DELETE FROM mtm_autori_knihy WHERE knihy_id='"+knihaId+"'";
		String sql3 = "DELETE FROM exemplare WHERE knihy_id ='"+knihaId+"'";
		String sql4 = "DELETE FROM knihy WHERE id ='"+knihaId+"'";

		try {
			pripojenie.getState().executeUpdate(sql1);
			pripojenie.getState().executeUpdate(sql2);
			pripojenie.getState().executeUpdate(sql3);
			pripojenie.getState().executeUpdate(sql4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBPripojenie.zobrazDatabazu();
		HlavneOkno.KnihyscrollPane = new JScrollPane(HlavneOkno.zoznam);
		
	}

	

}
