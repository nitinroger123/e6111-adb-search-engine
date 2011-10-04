import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONHelper {
	
	/**
	 * get the JSON results array for the query.
	 * @param search
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray getJSONArrayFromSearch(JSONObject search) throws JSONException {
		return search.getJSONObject("SearchResponse").getJSONObject("Web").getJSONArray("Results");
	}
	
	public static String getFormattedResults(JSONArray results) {
		return null;
	}
	
}
