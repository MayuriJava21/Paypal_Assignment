package com.paypal.bfs.test.employeeserv.validations;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paypal.bfs.test.employeeserv.model.EmployeeServiceExceptionResponse;

@RestControllerAdvice
public class EmployeeServiceValidation extends ResponseEntityExceptionHandler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<?> handleDateValidation(DateTimeParseException dfe) {
		return new ResponseEntity("Invalid Date", HttpStatus.BAD_REQUEST);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		EmployeeServiceExceptionResponse error = new EmployeeServiceExceptionResponse("Validation Failed", errors);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

}
