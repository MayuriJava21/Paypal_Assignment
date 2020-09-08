package com.paypal.bfs.test.employeeserv.impl;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.model.EmployeeServiceResponse;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;

/**
 * Implementation class for employee resource.
 * 
 * @author Mayuri
 */
@RestController
public class EmployeeResourceImpl {

	@Autowired
	private EmployeeRepository employeeRepository;

	private static HashSet<Integer> employeeCache = new HashSet<>();

	/**
	 * This method returns the employee details based on the Employee ID.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/bfs/employees/{id}")
	public ResponseEntity<Object> employeeGetById(@PathVariable("id") String id) {
		try {
			Employee emp = employeeRepository.findById(Integer.valueOf(id)).get();
			return new ResponseEntity<>(emp, HttpStatus.OK);
		} catch (NoSuchElementException exp) {
			EmployeeServiceResponse response = new EmployeeServiceResponse(
					"No employee with given id exists in database" + exp.getMessage(), HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	/**
	 * This method creates the employee only if the employee is new and not exists.
	 * 
	 * @param employee
	 * @return
	 */
	@PostMapping("/v1/bfs/employees")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
		EmployeeServiceResponse response = null;
		HttpStatus status = null;

		int employeeHashKey = employee.toString().hashCode();

		// Handling Idempotency to avoid duplicate resource creation
		if (employeeCache.contains(employeeHashKey)) {
			status = HttpStatus.OK;
			response = new EmployeeServiceResponse(
					"Employee with same data already exists in the database. Skipping duplicate creation.", status);
		} else {
			try {
				employeeRepository.save(employee);
				status = HttpStatus.CREATED;
				response = new EmployeeServiceResponse("Employee Created Successfully with id - " + employee.getId(),
						HttpStatus.CREATED);
				employeeCache.add(employeeHashKey);
			} catch (Exception exp) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				response = new EmployeeServiceResponse("Exception during employee creation - " + exp.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>(response, status);
	}

	/**
	 * This method validates the employee dateofbirth with localDate by checking the
	 * format
	 * 
	 * @param employee
	 *//*
		 * private void validate(@Valid Employee employee) {
		 * LocalDate.parse(employee.getDateOfBirth()); }
		 */
	/**
	 * This methods returns all the employee resources that are created
	 * 
	 * @return
	 */
	@GetMapping("/v1/bfs/employees/all")
	public ResponseEntity<List<Employee>> getAllEmployees() {

		List<Employee> emp = employeeRepository.findAll();
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

}
