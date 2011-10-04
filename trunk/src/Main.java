import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args []) throws IOException{
		String query = "";
		String requestURL = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		query = reader.readLine();
		System.out.println(query);
		requestURL = BingSearch.getRequestString(query);
		
	}


}
