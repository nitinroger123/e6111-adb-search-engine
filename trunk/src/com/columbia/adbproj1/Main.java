package com.columbia.adbproj1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	static Double currentPrecision;

	/**
	 * Display results and get Y N input
	 * Also populate list of irrelevant docs
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
			try {
				System.out.println("URL: " + obj.get("Url"));
				System.out.println("Title: " + obj.get("Title"));
				System.out.println("Summary: " + obj.get("Description"));
			} catch (JSONException exe) {
				System.out.println("[[[[JSON Exception]]]] occureed - "
						+ exe.getMessage());
			}
			System.out.println("]");
			System.out.print("Relevant (Y/N)?");
			inp = reader.readLine();
			inp = inp.trim();
			if (inp.equalsIgnoreCase("Y")) {
				relevant.add(new ResultDocument(i, obj.get("Title").toString(),
						obj.get("Description").toString(), obj.get("Url")
								.toString()));
			} else {
				irRelevant.add(new ResultDocument(i, obj.get("Title")
						.toString(), obj.get("Description").toString(), obj
						.get("Url").toString()));

			}
		}
		currentPrecision = Double.parseDouble(relevant.size() + "") / 10;
	}
	
	/**
	 * Main entry point to application. 
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void main(String args[]) throws IOException, JSONException {
		Double precision = 1.0;
		String appID = "E69E241D81BD12B3CAB2FAC07061D2DA6C00117E";
		String query = "";
		try {
			appID = args[0];
			System.out.println(args[0]);
			precision = Double.parseDouble(args[1]);
			for (int i = 2; i < args.length; i++) {
				System.out.println(args[i]);
				query += args[i] + " ";
			}
			query = query.trim();
		} catch (Exception e) {
			System.err.println(" Illegal Arguments " + e.getMessage());
			System.exit(1);
		}

		if (appID.equals("")) {
			appID = "E69E241D81BD12B3CAB2FAC07061D2DA6C00117E";
		}

		while (true) {
			ArrayList<ResultDocument> relevantDocs = runSearch(appID, query);

			if (currentPrecision == 0) {
				System.out
						.println(" Zero Relevant documents found. Terminating ");
				break;
			}

			if (currentPrecision >= precision) {
				System.out.println("Desired precision reached! With query: "
						+ query);
			}

			List<String> topQueryWords = getTopQueryWords(relevantDocs, 2);
			query = expandQuery(query, topQueryWords);
		}

	}
	
	/**
	 * Run the search and set weights for each term in the relevant documents
	 * @param appID
	 * @param query
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	private static ArrayList<ResultDocument> runSearch(String appID,
			String query) throws JSONException, IOException {

		String requestURL;
		ArrayList<ResultDocument> relevantDocs = new ArrayList<ResultDocument>();
		ArrayList<ResultDocument> irRelevantDocs = new ArrayList<ResultDocument>();
		HashMap<String, Integer> idfMap = new HashMap<String, Integer>();
		requestURL = BingSearch.getRequestString(query, appID);
		JSONObject result = BingSearch.getSearchResults(requestURL);
		JSONArray results = JSONHelper.getJSONArrayFromSearch(result);
		displayAndPopulateRelevant(results, relevantDocs, irRelevantDocs);
		idfMap = IdfHelper.getIdfMap(relevantDocs, irRelevantDocs);
		for (ResultDocument doc : relevantDocs) {
			for (String term : doc.termFrequency.keySet()) {
				doc.setWeight(term, idfMap.get(term));
			}
		}

		return relevantDocs;
	}
	
	/**
	 * Method to find the top 2 query words 
	 * @param relDocs
	 * @param expansionFactor
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<String> getTopQueryWords(
			ArrayList<ResultDocument> relDocs, int expansionFactor) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		for (ResultDocument doc : relDocs) {
			for (String term : doc.weightOfTerms.keySet()) {
				if (StopWords.stopWordsList.contains(term))
					continue;
				if (result.get(term) == null) {
					result.put(term, doc.weightOfTerms.get(term));
				} else {
					result.put(term,
							doc.weightOfTerms.get(term) + result.get(term));
				}
			}
		}
		ArrayList as = new ArrayList(result.entrySet());
		Collections.sort(as, new MyComparator());

		List<String> queryWords = new ArrayList<String>();
		int count = 0;
		Iterator i = as.iterator();
		while (i.hasNext()) {
			count++;
			queryWords.add(((Map.Entry<String, Double>) i.next()).getKey());
			if (count == 2) {
				break;
			}
		}

		return queryWords;
	}
	
    /**
     * Add top 2 words to the query.
     * @param query
     * @param wordsToBeAddedToQuery
     * @return
     */
	private static String expandQuery(String query,
			List<String> wordsToBeAddedToQuery) {
		String expandedQuery = query;
		for (String s : wordsToBeAddedToQuery) {
			expandedQuery += " " + s;
		}
		return expandedQuery;
	}

}

/**
 * 
 * The custom comparator to sort the values.
 *
 */
class MyComparator implements Comparator<Map.Entry<String, Double>> {

	@Override
	public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {

		Double first = o1.getValue();
		Double second = o2.getValue();
		return second.compareTo(first);
	}

}
