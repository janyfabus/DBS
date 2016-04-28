package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.json.JSONObject;

public class ESPripojenie {
	public void nieco(){

		try  {
			StringBuilder builder = new StringBuilder();
			URL url = new URL("http://localhost:9200/kniznica/knihy/_search");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		    for (String line; (line = reader.readLine()) != null;) {
//		        System.out.println(line);
		        builder.append(line + "\n");
		    }
	        System.out.println(builder);
	        JSONObject o = new JSONObject(builder.toString());
	        
//	        System.out.println(o.getJSONObject("hits").getJSONArray("hits").getJSONObject(2).getJSONObject("_source").get("meno"));
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://localhost:9200/").openConnection()));
//
//			httpcon.setDoOutput(true);
//			httpcon.setRequestProperty("Content-Type", "application/json");
//			httpcon.setRequestProperty("Accept", "application/json");
//			httpcon.setRequestMethod("POST");
//			httpcon.connect();
//			
//			byte[] outputBytes = "{'value': 7.5}".getBytes("UTF-8");
//			OutputStream os = httpcon.getOutputStream();
//
//			System.out.println(httpcon.getResponseMessage());
//			os.write(outputBytes);
//			
//			os.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
