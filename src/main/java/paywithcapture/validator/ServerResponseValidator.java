package paywithcapture.validator;

import org.apache.commons.httpclient.HttpMethod;

import paywithcapture.type.ServerResponse;

/*
 * This class is responsible for validating the response 
 * from the server after when the sendRequest methodin ServerRequestBuilder
 * comes back with a response
 */
public class ServerResponseValidator {
	
	/*
	 * static
	 * @param responseCode. Server response code
	 * @param responseObject. ServerResponse type for checking for other types of
	 * API errors in the response json.
	 */
	public static void validate(ServerResponse response) {
		
	}
}
