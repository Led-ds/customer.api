package com.builders.api.customer.error;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class CustomerErrorException extends CustomerBusinessException{

	public CustomerErrorException(String code, HttpStatus status) {
		super(code, status);
	}

}
