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

