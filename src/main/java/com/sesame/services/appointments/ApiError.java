package com.sesame.services.appointments;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Custom error message, created for API exception responses.
 * 
 * @author ngaspar
 * @version 1.0
 */
public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BusinessRules.DATETIME_PATTERN)
	private LocalDateTime timestamp;
	private String message;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = ex.getMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
