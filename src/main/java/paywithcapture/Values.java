package paywithcapture;

import java.util.HashMap;
import java.util.Map;

/*
 * This class hold string constants which include
 * server path, endpoint parts, error messages, 
 * and other english word or phrases needed in this application
 */
public class Values {
	
	public static Map<String, String> BASE_URL = new HashMap<String, String>();
	static{
		BASE_URL.put("production", "");
		BASE_URL.put("staging", "https://pwcstaging.herokuapp.com/");
	}
	
	public static final String AUTHENTICATION_PATH = "/oauth/token";
	
	
}
