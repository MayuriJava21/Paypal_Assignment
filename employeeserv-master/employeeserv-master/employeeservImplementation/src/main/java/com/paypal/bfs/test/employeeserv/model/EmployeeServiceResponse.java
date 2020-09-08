package com.paypal.bfs.test.employeeserv.model;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Mayuri
 *
 */
public class EmployeeServiceResponse {
	private String message;
	
	private HttpStatus status;

	public EmployeeServiceResponse(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
