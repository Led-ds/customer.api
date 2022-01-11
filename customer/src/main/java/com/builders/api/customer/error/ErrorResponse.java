package com.builders.api.customer.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import static lombok.AccessLevel.PRIVATE;
import lombok.RequiredArgsConstructor;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@RequiredArgsConstructor(access = PRIVATE)
public class ErrorResponse {

	private final int statusCode;
	
	private final List<ApiError> errors;
	
	static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
		return new ErrorResponse(status.value(), errors);
	}
	
	
	static ErrorResponse of(HttpStatus status, ApiError error) {
		return of(status, Collections.singletonList(error));
	}
	
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	@RequiredArgsConstructor
	static class ApiError {
		private final String code;
		private final String message;
	}
}
