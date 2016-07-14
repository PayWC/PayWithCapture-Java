package paywithcapture.builder;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import paywithcapture.type.ServerResponse;
import paywithcapture.validator.ServerResponseValidator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
 * ServerRequestBuilder is responsible for wrapping the apache httpclient
 * library into an easily useable class for interracting with http 
 * requests on the PayWithCapture Server
 * @author Ridwan Olalere
 * @version 0.0.1
 */
public class ServerRequestBuilder {
	
	private Map<String, String> urlParameters = new HashMap<String, String>();
	private Map<String, String> headers = new HashMap<String, String>();
	
	/*
	 * this property holds the data to be sent with the post request 
	 */
	private Map<String, String> postBody = new HashMap<String, String>();
	
	private HttpRequest request;
	
	private final String baseUrl;
	private String path;
	
	private String accessToken;
	
//	private Header[] cookies;
	
	private ServerResponse serverResponse;
	/*
	 * var is a log4j Logger instance responsible for logging.
	 */
	private static final Logger logger = Logger.getLogger(ServerRequestBuilder.class);
	
	/*
	 * constructor
	 */
	public ServerRequestBuilder(String url) {
		baseUrl = url;
	}
	
	public ServerRequestBuilder addAccessToken(String token) {
		this.accessToken = token;
		return this;
	}
	
	/*
	 * This is responsible for adding headers to the request
	 * after the request is set to either GetMethod or PostMethod
	 */
	private void setAccessToken() {
		request.header("Authorization", "Bearer " + this.accessToken);
	}
	
	/*
	 * responsible for setting the params from the client on the 
	 * request object
	 */
	private void setRequestParameters() {
		if (urlParameters != null) {
			for (String parameterKey: urlParameters.keySet()) {
				request.queryString(parameterKey, urlParameters.get(parameterKey));
			}
		}
	}
	
	/*
	 * holds value to be added to the header before request to the 
	 * PayWithCapture Server
	 * @param name. The name of the header to be added
	 * @param value. The value to be added to the header
	 */
	public ServerRequestBuilder addHeader(String name, String value) {
		headers.put(name, value);
		return this;
	}
	
	/*
	 * holds value to be added to the post or get request
	 * @param name. The name of the parameter POST or GET
	 * @param value. The value of the paramter POST or GET
	 */
	public ServerRequestBuilder addParameter(String name, String value) {
		urlParameters.put(name, value);
		return this;
	}
	
	/*
	 * This method helps to add post request body
	 */
	public ServerRequestBuilder addBody(String name, String value) {
		postBody.put(name, value);
		return this;
	}
	
	
	/*
	 * holds cookie to be added to the request object
	 * 
	 */
//	public ServerRequestBuilder addCookies(Header[] cookies) {
//		//because httpclient handles cookies as headers
//		//the header[] in string format returns Set-Cookie: a=6454;name=ridwan;
//		//
//		this.cookies = cookies;
//		return this;
//	}
	
	/*
	 * responsible for setting the cookies to the request object
	 * to be sent to the server
	 */
//	private void setCookieHeaders() {
//		if (this.cookies != null) {
//			for (Header h: this.cookies) {
//				//TODO: set cookies
//			}
//		}
//	}
	
	
	/*
	 * This method is responsible for preparing a request
	 * can prepare a get or post request
	 * @param typ. The type of the request. can be get or post.
	 * @param absoluteUrl. The absolute Url.
	 */
	private void prepareRequest(String typ, String absoluteUrl) {
		
		if (typ.equals("get")){
			request = Unirest.get(absoluteUrl);
		}	
		else{
			request = Unirest.post(absoluteUrl);
		}
			
	}
	
	/*
	 * converts the postBody map into post request body string in the format
	 * key=value&key=value
	 */
	private String buildPostBody() {
		String body = "";
		for (String k: postBody.keySet()) {
			body += k+"="+postBody.get(k)+"&";
		}
		return body;
	}
	
	/*
	 * set the post request data content added
	 */
	private void setPostRequestData() {	
		((HttpRequestWithBody) request).body(buildPostBody());
	}
	
	/*
	 * This function sets the request method content-type header to
	 * application/x-www-formurlencoded which is needed for the server
	 * to response appropriately
	 */
	private void setPostRequestContentTypeHeader() {
		request.header("Content-Type", "application/x-www-form-urlencoded");
	}
	
	/*
	 * This method is responsible for sending the request to the server
	 * validates the response with ServerResponseValidator
	 * and set the ServerRequestBuilder response
	 */
	private void sendRequest() {
		
			try {
//				logger.info("Post body in sendRequest: " + request.getBody().getEntity().toString());
//				request.body(postBody);
				HttpResponse<String> response = request.asString();
				ServerResponse sResponse = new ServerResponse(response);
				ServerResponseValidator.validate(sResponse);
				setResponse(sResponse);
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	private void setResponse(ServerResponse resp) {
		serverResponse = resp;
	}
	
	private ServerResponse getResponse() {
		return serverResponse;
	}
	
	/*
	 *  responsible for building and sending request to the server
	 *  validates server response before returning
	 *  
	 */
	public ServerResponse makePostRequest(String path) throws MalformedURLException {
		logger.info("Post body sent: " + postBody.toString());
		String absoluteUrl = new URL(new URL(baseUrl), path).toString();
		prepareRequest("post", absoluteUrl);
		setAccessToken();
		setPostRequestData();
		setPostRequestContentTypeHeader();
		sendRequest();
		return getResponse();
	}
	
	/*
	 * 
	 */
	public ServerResponse makeGetRequest(String path) throws MalformedURLException {
		String absoluteUrl = new URL(new URL(baseUrl), path).toString();
		prepareRequest("get", absoluteUrl);
		setAccessToken();
		setRequestParameters();
		sendRequest();
		return getResponse();
	}

}
