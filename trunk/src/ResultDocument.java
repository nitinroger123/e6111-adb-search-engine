import java.util.HashMap;


public class ResultDocument {
	
	HashMap<String, Integer> termFrequency;
	Integer docId;
	String title;
	String description;
	String url;
	
	/**
	 * Construct a new Document and compute the TF
	 * @param docId
	 * @param title
	 * @param description
	 * @param url
	 */
	public ResultDocument(Integer docId, String title, String description, String url) {
		termFrequency = new HashMap<String, Integer>();
		this.docId = docId;
		this.title = title.toLowerCase();
		this.url = url.toLowerCase();
		this.description = description.toLowerCase();
		constructTF();
	}
	
	/**
	 * Add a term to the map
	 * @param term
	 */
	public void addTerm(String term) {
		if (term == "") 
			return;
		Integer count = termFrequency.get(term);
		if (count == null) {
			termFrequency.put(term, 1);
		}
		else {
			termFrequency.put(term, count+1);
		}
	}
	
	/**
	 * Useless method to get the term frequency
	 * @param term
	 * @return
	 */
	public Integer getFrequency(String term) {
		return termFrequency.get(term);
	}
	
	//TODO: Split and populate map
	public void constructTF() {
	}
}
