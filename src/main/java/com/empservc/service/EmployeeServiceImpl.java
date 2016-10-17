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

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void registerEmployee(Employee employee) throws EmployeeServiceException {
		LOGGER.debug("registerEmployee()-start");
		if (!this.employeeServiceDao.isEmployeeExists(employee)) {
			this.employeeServiceDao.registerEmployeePersonalInfo(employee);
		} else {
			throw new EmployeeServiceException("employee already exists.");
		}
		LOGGER.debug("registerEmployee()-end");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteEmployee(Long employeeId) throws EmployeeServiceException {
		LOGGER.debug("deleteEmployee()-start");
		this.employeeServiceDao.deleteEmployeeInfo(employeeId);
		LOGGER.debug("deleteEmployee()-end");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateEmployee(Employee employee, String flagUpdate) throws EmployeeServiceException {
		LOGGER.debug("updateEmployee()-start");
		if (this.employeeServiceDao.isEmployeeExists(employee) || "personal".equals(flagUpdate)) {
			switch (flagUpdate) {
				case "personal" : this.employeeServiceDao.updateEmployeePersonalInfo(employee); break;
				case "address" :  this.employeeServiceDao.updateEmployeeAddressInfo(employee); break;
				case "contact" :  this.employeeServiceDao.updateEmployeeContactInfo(employee); break;
				default: throw new EmployeeServiceException("flagUpdate not found.");
			}
		}
		LOGGER.debug("updateEmployee()-end");
	}
}
