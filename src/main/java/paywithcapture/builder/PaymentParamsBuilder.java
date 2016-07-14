package paywithcapture.builder;

import java.math.BigDecimal;

/*
 * This PaymentParamsBuilder is responsible for setting Authentication parameters
 * A PaymentParamsBuilder is used so that can allow for easy setting of optional paramters in the 
 * future
 * new AccountPayment().addAmount().addType();
 */
public class PaymentParamsBuilder {
	
	private BigDecimal amount;
	private String description;
	private String accountNumber;
	private String transactionId;
	private String merchantId;
	private String cardNo;
	private String expMonth;
	private String expYear;
	private String cvv;
	private String bvn;
	private String pin;
	private String redirectUrl;
	
	public PaymentParamsBuilder addCardNo(String no) {
		this.cardNo = no;
		return this;
	}
	
	public PaymentParamsBuilder addExpMonth(String expMth) {
		this.expMonth = expMth;
		return this;
	}
	
	public PaymentParamsBuilder addCvv(String cv) {
		this.cvv = cv;
		return this;
	}
	
	public PaymentParamsBuilder addBvn(String bv) {
		this.bvn = bv;
		return this;
	}
	
	public PaymentParamsBuilder addPin(String pin) {
		this.pin = pin;
		return this;
	}
	
	public PaymentParamsBuilder addRedirectUrl(String url) {
		this.redirectUrl = url;
		return this;
	}
	
	public PaymentParamsBuilder addExpYear(String yr) {
		this.expYear = yr;
		return this;
	}
	
	public PaymentParamsBuilder addAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}
	
	public PaymentParamsBuilder addDescription(String desc) {
		this.description = desc;
		return this;
	}
	
	public PaymentParamsBuilder addAccountNumber(String number) {
		this.accountNumber = number;
		return this;
	}
	
	public PaymentParamsBuilder addTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}
	
	public PaymentParamsBuilder addMerchantId(String merchantId) {
		this.merchantId = merchantId;
		return this;
	}
	
	public String getCardNo() {
		return this.cardNo;
	}
	
	public String getExpMonth() {
		return this.expMonth;
	}
	
	public String getCvv() {
		return this.cvv;
	}
	
	public String getPin() {
		return this.pin;
	}
	
	public String getExpYear() {
		return this.expYear;
	}
	
	public String getBvn() {
		return this.bvn;
	}
	
	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getMerchantId() {
		return merchantId;
	}
}

