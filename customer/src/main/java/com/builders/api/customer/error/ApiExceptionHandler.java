package com.builders.api.customer.error;

import java.util.Locale;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.builders.api.customer.error.ErrorResponse.ApiError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.RequiredArgsConstructor;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

	private static final String NO_MESSAGE_AVAILABLE = "No message available";
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

	private final MessageSource apiErrorMessageSource;

	public ApiError toApiError(String code, Locale locale, Object... args) {
		String message;
		try {
			message = apiErrorMessageSource.getMessage(code, args, locale);

		}catch(NoSuchMessageException e) {
			message = NO_MESSAGE_AVAILABLE;
			LOGGER.error("Could not find any message for {} code under {} locale", code, locale);
		}

		return new ApiError(code, message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException ex, Locale locale){

		Stream<ObjectError> errors = ex.getBindingResult().getAllErrors().stream();

		List<ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage)
				.map(code -> toApiError(code, locale))
				.collect(toList());

		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);

		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale){
		final String errorCode = "generic.error-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ErrorResponse  errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getValue()));

		return ResponseEntity.badRequest().body(errorResponse);
	}


	@ExceptionHandler(CustomerBusinessException.class) 
	public ResponseEntity<ErrorResponse> handleCustomerException(CustomerBusinessException exception, Locale locale){
		final String errorCode = exception.getCode(); 
		final HttpStatus status = exception.getStauts();
		
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception));

		return ResponseEntity.badRequest().body(errorResponse); 
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception, Locale locale){
		LOGGER.error("Error not expected", exception);
		
		final String errorCode = "generic.internal.error";
		final HttpStatus status =  HttpStatus.INTERNAL_SERVER_ERROR;
		
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
		
		return ResponseEntity.status(status).body(errorResponse);
	}


}
