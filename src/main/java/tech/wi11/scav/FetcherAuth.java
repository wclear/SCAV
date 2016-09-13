package tech.wi11.scav;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Class used to authenticate request to SpeedCurve API.
 */
public class FetcherAuth extends Authenticator {
	
	/**
	 * The API key used to authenticate.
	 */
	private final String key;

	/**
	 * Initializes a new instance of the FetcherAuth class with a SpeedCurve API key.
	 * @param apiKey 
	 */
	public FetcherAuth(String apiKey) {
		this.key = apiKey;
	}

	/**
	 * Returns the password authentication.
	 * @return 
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.key, new char[0]);
	}
}
