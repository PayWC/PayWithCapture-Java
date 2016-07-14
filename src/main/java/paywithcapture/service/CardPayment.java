package paywithcapture.service;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import paywithcapture.Values;
import paywithcapture.builder.PaymentParamsBuilder;
import paywithcapture.builder.ServerRequestBuilder;
import paywithcapture.contract.PaymentContract;
import paywithcapture.type.ServerResponse;

/*
 * This class is responsible  
 */
public class CardPayment implements PaymentContract{
	private String env;
	private Authentication auth;
	
	private static final Logger logger = Logger.getLogger(CardPayment.class);
	
	public CardPayment(String clientId, String clientSecret, String env) {
		this.env = env;
		auth = new Authentication(new Authentication.Builder().addClientId(clientId)
										.addClientSecret(clientSecret)
										.addEnvironment(env)
									).run();
	}
	
	public JSONObject createPayment(PaymentParamsBuilder builder) {
		// TODO Auto-generated method stub
		ServerRequestBuilder server = new ServerRequestBuilder(Values.BASE_URL.get(this.env))
											.addAccessToken(this.auth.getAccessToken())
											.addBody("type", "card")
											.addBody("amount", String.valueOf(builder.getAmount()))
											.addBody("description", builder.getDescription())
											.addBody("transaction_id", builder.getTransactionId())
											.addBody("merchant_id", builder.getMerchantId())
											.addBody("cardno", builder.getCardNo())
											.addBody("expmth", builder.getExpMonth())
											.addBody("expyear", builder.getExpYear())
											.addBody("cvv", builder.getCvv());
	
		if (builder.getPin() != null)
			server.addBody("pin", builder.getPin());
		
		if (builder.getBvn() != null)
			server.addBody("bvn", builder.getBvn());
			
		if (builder.getRedirectUrl() != null)
			server.addBody("redirect_url", builder.getRedirectUrl());
		
		ServerResponse response = null;
		
		try {
			response = server.makePostRequest(Values.PAYMENT_PATH);
		} catch (MalformedURLException e) {
			logger.debug("MalformedformedUrl in CardPayment");
			e.printStackTrace();
		}
		
		return response.getBody();
	}

	public JSONObject validatePayment() {
		return null;
	}

}
