package com.columbia.adbproj1;
import java.util.HashMap;
import java.util.Map;

public class ResultDocument {
	
	HashMap<String, Double> termFrequency;
	Integer docId;
	String title;
	String description;
	String url;
	Map<String, Double> weightOfTerms;
	
	/**
	 * Construct a new Document and compute the TF
	 * @param docId
	 * @param title
	 * @param description
	 * @param url
	 */
	public ResultDocument(Integer docId, String title, String description, String url) {
		termFrequency = new HashMap<String, Double>();
		weightOfTerms = new HashMap<String, Double>();
		this.docId = docId;
		this.title = title.toLowerCase();
		this.url = url.toLowerCase();
		this.description = description.toLowerCase();
		constructTF(this.title, 1.2);
		constructTF(this.description, 1.0);
	}
	
	/**
	 * Add a term to the map
	 * @param term
	 */
	public void addTerm(String term, Double weight) {
		
		if (term == "") 
			return;
		Double count = termFrequency.get(term);
		if (count == null) {
			termFrequency.put(term, 1*weight*(11.0 - this.docId));
			
		}
		else {
			termFrequency.put(term, (count+1)*weight);
		}
	}
	
	/**
	 * Useless method to get the term frequency
	 * @param term
	 * @return
	 */
	public Double getFrequency(String term) {
		return termFrequency.get(term);
	}
	
	/**
	 * Split and populate the map 
	 * @param str
	 * @param weight
	 */
	public void constructTF(String str, Double weight) {
		String regex = "[^a-zA-Z0-9]";
		String tempTitle [] = str.split(regex);
		for (int i=0; i< tempTitle.length; i++) {
			String term = tempTitle[i];
			if (term != "" || term != null) {
				addTerm(term, weight);
			}
		}
		
	}
	/**
	 * set the weights for each term
	 * @param term
	 * @param idfValue
	 */
	public void setWeight(String term, Integer idfValue) {
		if (idfValue == null) {
			weightOfTerms.put(term, 0.0);
			return;
		}
		Double weight = termFrequency.get(term)*Math.log10(10.0/(1.0 + idfValue));
		weightOfTerms.put(term, weight);
	}
	/**
	 * Helper method to test our code.
	 */
	public void printWeights() {
		for (String term : weightOfTerms.keySet()) {
			System.out.println(this.title+" : "+term+" : "+ weightOfTerms.get(term));
		}
	}
	
}
