package com.oranbyte.ecom.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ValidationErrorResponse response = new ValidationErrorResponse(false, "Validation failed",
				HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(AppException.class)
	public ResponseEntity<?> handleBusinessException(AppException ex) {

		return AppUtils.getApiResponse(ex.getStatus(), false, ex.getMessage(), null);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException ex) {

		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
				"File operation failed: " + ex.getMessage(), null);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {

		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage(), null);
	}
}