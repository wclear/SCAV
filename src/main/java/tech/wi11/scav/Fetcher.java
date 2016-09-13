package tech.wi11.scav;

import com.google.gson.Gson;
import tech.wi11.scav.models.ScUrl;
import tech.wi11.scav.models.Site;
import tech.wi11.scav.models.Sites;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Fetches data from the SpeedCurve API.
 */
public class Fetcher {
	
	/**
	 * Sets the authentication to be used when making requests.
	 * @param auth 
	 */
	public static void setApiKey(FetcherAuth auth) {
		Authenticator.setDefault(auth);
	}
	
	/**
	 * Sends a GET request to the given URL and returns the response as text.
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException 
	 */
	private static String makeRequest(String url) throws MalformedURLException, IOException {
		URL apiUrl = new URL(url);
		HttpsURLConnection connection = (HttpsURLConnection)apiUrl.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String response = "";
		String line;
		while ((line = reader.readLine()) != null) {
			response += line;
		}
		return response;
	}
	
	/**
	 * Returns a sites object containing basic site info about sites being tested by SpeedCurve.
	 * @return
	 * @throws IOException 
	 */
	public static Sites getSites() throws IOException {
		Gson gson = new Gson();
		
		// TODO: Store the URL in config or get it from the user.
		String response = Fetcher.makeRequest("https://api.speedcurve.com/v1/urls");
		Sites sites = gson.fromJson(response, Sites.class);
		return sites;
	}
	
	/**
	 * Gets a site and its URLs.
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public static Site getSite(int id) throws IOException {
		Gson gson = new Gson();
		String response = Fetcher.makeRequest(String.format("https://api.speedcurve.com/v1/sites/%d", id));
		Site site = gson.fromJson(response, Site.class);
		return site;
	}
	
	/**
	 * Retrieves a URL which includes all tests for that URL.
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public static ScUrl getUrl(int id) throws IOException {
		Gson gson = new Gson();
		String response = Fetcher.makeRequest(String.format("https://api.speedcurve.com/v1/urls/%d", id));
		ScUrl url = gson.fromJson(response, ScUrl.class);
		return url;
	}
}
