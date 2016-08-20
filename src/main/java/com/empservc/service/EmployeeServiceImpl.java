package com.empservc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empservc.dao.EmployeeServiceDao;
import com.empservc.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeServiceDao employeeServiceDao;
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = null;
		employees = this.employeeServiceDao.getAllEmployees();
		if (employees != null && !employees.isEmpty()) {
			System.out.println("From database:");
			for (Employee emp : employees) {
				System.out.println(emp);
			}
		}
		
		return employees;
	}


}
