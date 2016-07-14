package paywithcapture;

import java.math.BigDecimal;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import paywithcapture.builder.PaymentParamsBuilder;
import paywithcapture.builder.ServerRequestBuilder;
import paywithcapture.service.AccountPayment;
import paywithcapture.service.Authentication;
import paywithcapture.type.ServerResponse;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private static final Logger logger = Logger.getLogger(AppTest.class);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * test the server request builder works fine
     */
//    public void testServerRequestBuilderReturns404()
//    {
//        try {
//			ServerResponse response = new ServerRequestBuilder("http://pwchostedstaging.herokuapp.com")
//											.addParameter("name", "ridwan")
//											.addHeader("Authentication", "Bearer 7646474hdgrtr6")
//											.makeGetRequest("/oauth/token");
//			this.assertEquals(404, response.getStatusCode());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//    }
    
    /*
     * test that the response from the server
     * during authentication is valid
     */
    public void testAuthenticationServiceOk() {
    	Authentication.Builder builder = new Authentication.Builder()
    			.addClientId("577e5fe42989c31100b26f14")
    			.addClientSecret("diHopa8yFNDWofRNJIeREDmAV3HhL7bwr4umhlhPS0CgqIiOylA6Y9obfsV9VsbWBDuMUKE7MvVpIrtip4oX8zmG21I4QI1rhwjx");
    	Authentication auth = new Authentication(builder).run();
    	this.assertNotNull(auth.getAccessToken());
    	this.assertNotNull(auth.getRefreshToken());
    	this.assertFalse(auth.getExpiresIn() == 0);
    	logger.info("AccessToken returned from Authentication is" + auth.getAccessToken());
    }
    
    
    /*
     * test account payment
     */
    public void testAccountPayment() {
    	PaymentParamsBuilder params = new PaymentParamsBuilder()
    									.addAmount(new BigDecimal("1000"))
    									.addAccountNumber("0690000032")
    									.addDescription("Ridwan testing java lib")
    									.addMerchantId("577e5fe42989c31100b26f13")
    									.addTransactionId(String.valueOf(System.currentTimeMillis()));
    	AccountPayment client = new AccountPayment("577e5fe42989c31100b26f14", 
    			"diHopa8yFNDWofRNJIeREDmAV3HhL7bwr4umhlhPS0CgqIiOylA6Y9obfsV9VsbWBDuMUKE7MvVpIrtip4oX8zmG21I4QI1rhwjx", 
    			"staging");
    	JSONObject response = client.createPayment(params);
    	logger.info("JSONObject response" + response);
    }
}
