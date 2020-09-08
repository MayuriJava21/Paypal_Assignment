/**
 * 
 */
package com.paypal.bfs.test.employeeserv.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;

/**
 * @author Mayuri
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
public class EmployeeRestCodesTest {

	HttpHeaders headers = null;
	HttpEntity<String> entity = null;
	 ResponseEntity<String> response = null;

	@Autowired
	private TestRestTemplate restTemplate;

	@Mock
	private EmployeeRepository repo;
	
	@Before
	public void setup() throws Exception {
		 headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);

	}

	

	/*
	 * {
    "firstName":null ,
    "lastName": " ",
    "dateOfBirth": "",
    "address":
    	{
    		"line1" : "",
    		"line2": "",
    		"city" : "",
    		"state" : "",
    		"zipCode": ""
    	}
}
	 */
	@Test
	public void emptyEmployee_emptyAddress_save_400() throws JSONException {

		String empInJson = "{\r\n" + "    \"firstName\":\"\" ,\r\n" + "    \"lastName\": \"\",\r\n"
				+ "    \"dateOfBirth\": \"\",\r\n" + "    \"address\":\r\n" + "    	{\r\n"
				+ "    		\"line1\" : \"\",\r\n" + "    		\"line2\": \"\",\r\n"
				+ "    		\"city\" : \"\",\r\n" + "    		\"state\" : \"\",\r\n"
				+ "    		\"zipCode\": \"\"\r\n" + "    	}\r\n" + "}";
		
		
		entity = new HttpEntity<>(empInJson, headers);
		
		// POST in Json format
		 response = restTemplate.postForEntity("/v1/bfs/employees", entity, String.class);

		String expectedJson = "{\"message\":\"Validation Failed\",\"errorInfo\":{\"firstName\":\"first Name must not be empty\",\"lastName\":\"last Name must not be empty\",\"address.city\":\"address.city must not be empty\",\"address.state\":\"address.state must not be empty\",\"address.line1\":\"address.line1 must not be empty\",\"dateOfBirth\":\"dateOfBirth is missing\",\"address.zipCode\":\"zip code must be five-digit and nine-digit (called ZIP + 4) formats\"}}";
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		JSONAssert.assertEquals(expectedJson, response.getBody(), false);

		verify(repo, times(0)).save(any(Employee.class));

	}

	/**
	 * 
	 * @throws JSONException
	 */
	
	/*{
    "firstName":"Jackson" ,
    "lastName": "Paypal",
    "dateOfBirth": "2002-09-06",
    "address":
    	{
    		"line1" : "339 Liverpool Street",
    		"line2": "",
    		"city" : "Plno",
    		"state" : "TX",
    		"zipCode": "1245"
    	}
}*/
	@Test
	public void invalidEmployee_save_400() throws JSONException {

		String empInJson = "{\r\n" + 
				"    \"firstName\":\"Jackson\" ,\r\n" + 
				"    \"lastName\": \"Paypal\",\r\n" + 
				"    \"dateOfBirth\": \"2002-09-06\",\r\n" + 
				"    \"address\":\r\n" + 
				"    	{\r\n" + 
				"    		\"line1\" : \"339 Liverpool Street\",\r\n" + 
				"    		\"line2\": \"\",\r\n" + 
				"    		\"city\" : \"Plno\",\r\n" + 
				"    		\"state\" : \"TX\",\r\n" + 
				"    		\"zipCode\": \"1245\"\r\n" + 
				"    	}\r\n" + 
				"}";

		 entity = new HttpEntity<>(empInJson, headers);

		response = restTemplate.exchange("/v1/bfs/employees", HttpMethod.POST, entity, String.class);
		
		String expectedJson = "{\"message\":\"Validation Failed\",\"errorInfo\":{\"address.zipCode\":\"zip code must be five-digit and nine-digit (called ZIP + 4) formats\"}}";
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		JSONAssert.assertEquals(expectedJson, response.getBody(), false);

	}

}
