import java.util.ArrayList;
import java.util.HashMap;


public class IdfHelper {
	public static HashMap<String, Integer> idfMap = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> getIdfMap(ArrayList<ResultDocument> relDocs) {
		for (ResultDocument doc: relDocs) {
			HashMap<String, Double> map = doc.termFrequency;
			for (String s: map.keySet()) {
				addTerm(s);
			}
		}
		return idfMap;
	}
	
	/**
	 * Add the term and increment the value by its frequency
	 * @param term
	 * @param freq
	 */
	private static void addTerm(String term) {
		if (term == "") 
			return;
		Integer count = idfMap.get(term);
		if (count == null) {
			idfMap.put(term, 1);
		}
		else {
			idfMap.put(term, (count+1));
		}
	}
}
