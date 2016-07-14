package paywithcapture.service;

import java.math.BigDecimal;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import paywithcapture.Values;
import paywithcapture.builder.PaymentParamsBuilder;
import paywithcapture.builder.ServerRequestBuilder;
import paywithcapture.contract.PaymentContract;
import paywithcapture.type.ServerResponse;

/*
 * This class is responsible for account payment
 * it implements payment contract which indicates the interface it any payment
 * class must oblige to
 */
public class AccountPayment implements PaymentContract {
	
	private final Authentication auth;
	private String env;
	
	private static final Logger logger = Logger.getLogger(AccountPayment.class);
	
	/*
	 * constructor
	 * @param clientId. You can find your clientId in the PayWithCapture DevCenter
	 * @param clientSecret. You can find your clientSecret in the PayWithCapture DevCenter
	 * @param env. Env can either be "staging" or "production" depending on the stage of 
	 * your development.
	 */
	public AccountPayment(String clientId, String clientSecret, String env) {
		this.env = env;
		Authentication.Builder authBuilder = new Authentication.Builder()
													.addClientId(clientId)
													.addClientSecret(clientSecret)
													.addEnvironment(env);
		auth = new Authentication(authBuilder).run();
	}
	
	/*
	 * This method builds and sends the server request needed for
	 * creation of payment on PayWithCapture API
	 */
	private JSONObject _create(PaymentParamsBuilder params) {
		JSONObject jsonResp = null;
		try {
			ServerResponse response = new ServerRequestBuilder(Values.BASE_URL.get(this.env))
											.addAccessToken(this.auth.getAccessToken())
											.addBody("type", "account") //the type for acc payment is account
											.addBody("amount", String.valueOf(params.getAmount()))
											.addBody("description", params.getDescription())
											.addBody("accountnumber", params.getAccountNumber())
											.addBody("merchant_id", params.getMerchantId())
											.addBody("transaction_id", params.getTransactionId())
											.makePostRequest(Values.PAYMENT_PATH);
			jsonResp = response.getBody();
			logger.info("Response from account payment is: " + jsonResp.toString());								
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResp;
	}
	
	public JSONObject createPayment(PaymentParamsBuilder params) {
		// TODO Auto-generated method stub
		return _create(params);
	}

	public JSONObject validatePayment() {
		// TODO Auto-generated method stub
		return null;
	}
}
