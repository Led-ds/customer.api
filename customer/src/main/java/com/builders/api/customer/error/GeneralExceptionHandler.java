package com.builders.api.customer.error;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GeneralExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionHandler.class);
	
	@Autowired
	private ApiExceptionHandler apiExceptionHandler;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception, Locale locale){
		LOGGER.error("Error not expected", exception);
		
		final String errorCode = "generic.internal.error";
		final HttpStatus status =  HttpStatus.INTERNAL_SERVER_ERROR;
		
		final ErrorResponse errorResponse = ErrorResponse.of(status, apiExceptionHandler.toApiError(errorCode, locale));
		
		return ResponseEntity.status(status).body(errorResponse);
	}
}
