package com.paypal.bfs.test.employeeserv.impl;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeControllerTest {

	@InjectMocks
	EmployeeResourceImpl controller;

	@Mock
	private EmployeeRepository repo;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGet() throws Exception {
		Employee emp = new Employee();

		when(repo.findById(1)).thenReturn(Optional.of(emp));

		mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}


	@Test
	public void testPost_validation() throws Exception {
		LocalDate date = LocalDate.now();
		Address address = new Address(1, "line1", "line2", "city", "state", "12345");
		Employee emp = new Employee(1, null, "lastName", date, address);

		when(repo.save(emp)).thenReturn(emp);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees").accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(emp)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

}
