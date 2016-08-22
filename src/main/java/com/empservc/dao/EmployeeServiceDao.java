package com.empservc.dao;

import java.util.List;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;

public interface EmployeeServiceDao {
	List<Employee> getAllEmployees();
	void registerEmployeePersonalInfo(Employee employee) throws EmployeeServiceException;
	boolean isEmployeeExists(Employee employee)  throws EmployeeServiceException;
	void updateEmployeePersonalInfo(Employee employee)  throws EmployeeServiceException;
	void updateEmployeeAddressInfo(Employee employee)  throws EmployeeServiceException;
	void updateEmployeeContactInfo(Employee employee)  throws EmployeeServiceException;
	void deleteEmployeeInfo(Long employeeId)  throws EmployeeServiceException;
}
