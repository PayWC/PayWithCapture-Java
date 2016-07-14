package paywithcapture.service;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import paywithcapture.Values;
import paywithcapture.builder.ServerRequestBuilder;
import paywithcapture.type.ServerResponse;

/*
 * This is the authentication class responsible for getting the accessToken 
 * needed by other requests to the PayWithCapture server
 * 
 */
public class Authentication {
	private ServerRequestBuilder reqBuilder;
	
	private String accessToken;
	private int expiresIn;
	
	/*
	 * This can be used to make refresh requests to the
	 * Authentication Server
	 */
	private String refreshToken;
	
	private static final Logger logger = Logger.getLogger(Authentication.class);
	
	/*
	 * constructor
	 * @param builder. The builder is an object used in setting needed parameters
	 */
	public Authentication(Builder builder) {
		String env = builder.env != null ? builder.env: "staging";
		reqBuilder = new ServerRequestBuilder(Values.BASE_URL.get(env));
		reqBuilder.addBody("client_id", builder.clientId)
				  .addBody("client_secret", builder.clientSecret)
				  .addBody("grant_type", "client_credentials");
	}
	
	/*
	 * This method is responsible for sending the request to the server using 
	 * the reqBuilder an instance of ServerRequestBuilder class to the server
	 * It then gets the server response converts the json response to JSONPATH
	 * and set the needed Authentication properties such as accessToken, expiresIn, refreshToken
	 * 
	 */
	public Authentication run() {
		try {
			ServerResponse response = reqBuilder.makePostRequest(Values.AUTHENTICATION_PATH);
			
			JSONObject jsonObject = response.getBody();
			setAuthenticationPropertiesFromResponse(jsonObject);
			
		} catch (MalformedURLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return this;
	}
	
	/*
	 * This method is responsible for converting the json response
	 * from the Authentication server into the properties of Authentication
	 * @param JSONObject obj
	 */
	private void setAuthenticationPropertiesFromResponse(JSONObject obj) {
		this.accessToken = (String) obj.get("access_token");
		this.refreshToken = (String) obj.get("refresh_token");
		
		String expiresInString = (String) obj.get("expires_in");
		this.expiresIn = Integer.valueOf(expiresInString);
	}
	
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
	
	public int getExpiresIn() {
		return this.expiresIn;
	}
	
	/*
	 * This is a builder class.
	 * It enables the client to build authentication parameters by
	 * optionally setting some params or not
	 * 
	 * e.g builder = Authentication.Builder().addClientId().addClientSecret();
	 * this builder can then be passed to authentication to decide on how to authenticate
	 * every request
	 *  new Authentication(builder);
	 * 
	 */
	public static class Builder {
		protected String clientId;
		protected String clientSecret;
		protected String env;
		
		
		public Builder addClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}
		
		public Builder addClientSecret(String secret) {
			this.clientSecret = secret;
			return this;
		}
		
		public Builder addEnvironment(String env) {
			this.env = env;
			return this;
		}
		
		
	}
}
