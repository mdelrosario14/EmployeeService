package com.empservc.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empservc.dao.EmployeeServiceDao;
import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeServiceDao employeeServiceDao;
	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = null;
		employees = this.employeeServiceDao.getAllEmployees();
		if (employees != null && !employees.isEmpty()) {
			for (Employee emp : employees) {
				System.out.println(emp);
			}
		}
		
		return employees;
	}

	@Transactional
	@Override
	public void registerEmployee(Employee employee) throws EmployeeServiceException {
		LOGGER.debug("registerEmployee()-start");
		this.employeeServiceDao.registerEmployeePersonalInfo(employee);
		
		LOGGER.debug("registerEmployee()-end");
	}


}
