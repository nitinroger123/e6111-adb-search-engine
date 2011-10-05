import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	/**
	 * Display results and get Y N input
	 * 
	 * @param results
	 * @param relevant
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void displayAndPopulateRelevant(JSONArray results,
			ArrayList<ResultDocument> relevant,
			ArrayList<ResultDocument> irRelevant) throws JSONException,
			IOException {
		String inp = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		for (int i = 0; i < results.length(); i++) {
			JSONObject obj = results.getJSONObject(i);
			System.out.println("Result " + i);
			System.out.println("[");
			System.out.println("URL: " + obj.get("Url"));
			System.out.println("Title: " + obj.get("Title"));
			System.out.println("Summary: " + obj.get("Description"));
			System.out.println("]");
			System.out.print("Relevant (Y/N)?");
			inp = reader.readLine();
			inp = inp.trim();
			if (inp.equalsIgnoreCase("Y")) {
				relevant.add(new ResultDocument(1, obj.get("Title").toString(),
						obj.get("Description").toString(), obj.get("Url")
								.toString()));
			} else {
				irRelevant.add(new ResultDocument(1, obj.get("Title").toString(),
						obj.get("Description").toString(), obj.get("Url")
								.toString()));

			}
		}
	}

	public static void main(String args[]) throws IOException, JSONException {
		String query = "";
		String requestURL = "";
		query = readQuery();
		ArrayList<ResultDocument> relevantDocs = new ArrayList<ResultDocument>();
		ArrayList<ResultDocument> irRelevantDocs = new ArrayList<ResultDocument>();
		requestURL = BingSearch.getRequestString(query);
		JSONObject result = BingSearch.getSearchResults(requestURL);
		JSONArray results = JSONHelper.getJSONArrayFromSearch(result);
		displayAndPopulateRelevant(results, relevantDocs, irRelevantDocs);
		System.out.println(relevantDocs.size());

	}

	private static String readQuery() throws IOException {
		String query;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		query = reader.readLine();
		return query;
	}

}
