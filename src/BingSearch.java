import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author nitin
 * The class to search bing with the 
 * supplied query.
 */
public class BingSearch {
	
	/* The constants required to build the request*/
	public static String requestString = "http://api.search.live.net/json.aspx?";
	public static final String sources = "Web";
	public static final String appId = "E69E241D81BD12B3CAB2FAC07061D2DA6C00117E";
	public static final String webCount = "10";
	
	public static String getRequestString(String query) {
		requestString = requestString + "Appid=" + appId + "&query="+query 
						+ "&sources=" + sources + "&web.count=" + webCount;
		System.out.println(requestString);
		return requestString;
	}
	
	private static String readAll(BufferedReader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    String s ="";
	    while ((s = rd.readLine()) != null) {
	      sb.append(s);
	    }
	    return sb.toString();
	  }
	
	public static JSONObject getSearchResults(String requestURL) throws JSONException, IOException {
		URL search = new URL(requestURL);
		URLConnection conn = search.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String resultString = readAll(in); 
		JSONObject result = new JSONObject(resultString);
		System.out.println(result.toString());
		return result;
	}

}
