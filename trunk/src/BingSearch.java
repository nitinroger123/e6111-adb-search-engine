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
		return requestString;
	}

}
