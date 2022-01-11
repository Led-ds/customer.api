package com.builders.api.customer.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@RequiredArgsConstructor
@Getter
public class CustomerBusinessException extends RuntimeException {

	private final String code;
	private final HttpStatus stauts;
	
}
