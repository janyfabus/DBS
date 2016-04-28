package mainpack;
import library.HlavneOkno;
import database.DBPripojenie;
import database.ESPripojenie;


public class MainKniznica {
	

	public static void main(String[] args) {
//		DBPripojenie pripojenie = new DBPripojenie();
//        new HlavneOkno(pripojenie);
		new ESPripojenie().nieco();
	}

}
