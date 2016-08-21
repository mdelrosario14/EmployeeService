package com.empservc.dao;

import java.util.List;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;

public interface EmployeeServiceDao {
	List<Employee> getAllEmployees();
	void registerEmployeePersonalInfo(Employee employee) throws EmployeeServiceException;
}
