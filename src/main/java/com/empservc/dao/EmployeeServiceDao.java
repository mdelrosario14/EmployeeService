package com.empservc.dao;

import java.util.List;

import com.empservc.model.Employee;

public interface EmployeeServiceDao {
	List<Employee> getAllEmployees();
}
