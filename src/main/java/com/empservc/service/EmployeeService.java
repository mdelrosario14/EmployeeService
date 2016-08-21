package com.empservc.service;

import java.util.List;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
	void registerEmployee(Employee employee) throws EmployeeServiceException;
}
