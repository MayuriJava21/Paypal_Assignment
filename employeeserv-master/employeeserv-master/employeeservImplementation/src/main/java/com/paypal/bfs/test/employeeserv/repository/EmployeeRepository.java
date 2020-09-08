/**
 * 
 */
package com.paypal.bfs.test.employeeserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.employeeserv.model.Employee;

/**
 * @author Mayuri
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	

}
