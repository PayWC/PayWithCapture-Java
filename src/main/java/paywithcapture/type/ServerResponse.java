package paywithcapture.type;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

/*
 * A representation of the response from the server
 * in easy to use format
 */
public class ServerResponse {
	
	private HttpResponse<String> response;
	private static final Logger logger = Logger.getLogger(ServerResponse.class);
	
	public ServerResponse(HttpResponse<String> resp) {
		response = resp;
	}
	
	public Headers getHeaders() {
		return response.getHeaders();
	}
	
	public String getHeader(String name) {
		return response.getHeaders().getFirst(name);
	}
	
	public String getCookies() {
		return response.getHeaders().getFirst("Set-Cookie");
	}
	
	public JSONObject getBody() {
		JsonNode body = new JsonNode(response.getBody());
		return body.getObject();
	}
	
	public int getStatusCode() {
		return response.getStatus();
	}
	
}
