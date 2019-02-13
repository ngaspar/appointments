package com.sesame.services.appointments;

import java.text.ParseException;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom exception intercepter/handler - allows for application-specific
 * exception responses, particularly useful when they are caused by user error
 * (e.g. missing or invalid fields)
 * 
 * @author ngaspar
 * @version 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ IllegalArgumentException.class, ParseException.class })
	protected ResponseEntity<Object> handleIllegalArgument(Exception ex) {
		return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler({ EntityNotFoundException.class, NoSuchElementException.class })
	protected ResponseEntity<Object> handleEntityNotFound(Exception ex) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message) {
		ApiError apiError = new ApiError(httpStatus, message);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}