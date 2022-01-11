package com.builders.api.customer.error;

@SuppressWarnings("serial")
public class CustomerExceptionHandler extends RuntimeException {

	private String messageContext;
	
	public CustomerExceptionHandler(String message) { super(message); }
	
	public CustomerExceptionHandler(String message, String messageC) {
        super(message);
        this.messageContext = messageC;
    }

	public String getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(String messageContext) {
		this.messageContext = messageContext;
	}
	
}
