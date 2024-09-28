package com.example.short_links.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Validation failed");
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ShortLinkNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleShortLinkNotFoundException(ShortLinkNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	