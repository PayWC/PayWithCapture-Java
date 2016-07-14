package paywithcapture.contract;

import org.json.JSONObject;

import paywithcapture.builder.PaymentParamsBuilder;

public interface PaymentContract {
	public JSONObject createPayment(PaymentParamsBuilder builder);
	public JSONObject validatePayment();
}
