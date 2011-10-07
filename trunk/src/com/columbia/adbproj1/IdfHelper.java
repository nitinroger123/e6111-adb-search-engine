package com.columbia.adbproj1;
import java.util.ArrayList;
import java.util.HashMap;


public class IdfHelper {
	public static HashMap<String, Integer> idfMap = new HashMap<String, Integer>();
	/**
	 * CAlculate the idf value for each term.
	 * @param relDocs
	 * @param irrelDocs
	 * @return
	 */
	public static HashMap<String, Integer> getIdfMap(ArrayList<ResultDocument> relDocs, ArrayList<ResultDocument> irrelDocs) {
		for (ResultDocument doc: relDocs) {
			HashMap<String, Double> map = doc.termFrequency;
			for (String s: map.keySet()) {
				addTerm(s);
			}
		}
		/* Irrelevant docs are also considered for idf */
		for (ResultDocument doc: irrelDocs) {
			HashMap<String, Double> map = doc.termFrequency;
			for (String s: map.keySet()) {
				addTerm(s);
			}
		}
		return idfMap;
	}
	
	/**
	 * Add the term and increment the value by 1 as it appears in one more doc.
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
