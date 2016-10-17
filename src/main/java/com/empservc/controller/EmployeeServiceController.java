package com.empservc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;
import com.empservc.service.EmployeeService;

/**
 * This class serves as the controller of the employee service module.
 * @author Mardolfh del Rosario
 *
 */
@RestController
@RequestMapping("/emp")
public class EmployeeServiceController {
	@Autowired
	EmployeeService employeeService;
	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceController.class);

	/**
	 * Registers a new employee.
	 * 
	 * @param employee Employee mapped from a json reference
	 * @return ResponseEntity object reference
	 */
	@RequestMapping(value="/register.do", method = RequestMethod.POST)
	public ResponseEntity<String> registerNewEmployee(@RequestBody Employee employee) {
		LOGGER.debug("registerNewEmployee()-start: " + employee);
		String responseCode = "";
		ResponseEntity<String> returnEntity = null;
		
		try {
			if (employee != null) {
				this.employeeService.registerEmployee(employee);
				returnEntity = new ResponseEntity<>("", HttpStatus.OK);
			} else {
				responseCode = "Employee parameters are null.";
				returnEntity = new ResponseEntity<String>(responseCode, HttpStatus.NO_CONTENT);
			}
			
		} catch (EmployeeServiceException e) {
			LOGGER.error("Failed to register employee...");
			if ("employee already exists.".equals(e.getMessage())) {
				responseCode = e.getMessage();
			} else {
				responseCode = "Failed to save to database.";
			}
			returnEntity = new ResponseEntity<String>(responseCode, HttpStatus.NOT_ACCEPTABLE);
		}
		
		LOGGER.debug("registerNewEmployee()-end");
		return returnEntity;
	}
	
	/**
	 * Updates an employee information.
	 * 
	 * @param employee  Employee mapped from a json reference
	 * @param flagUpdate flag if personal, address or contact
	 * @return ResponseEntity object reference
	 */
	@RequestMapping(value="/update.do", method = RequestMethod.PUT)
	public ResponseEntity<String> updateEmployeeInfo(@RequestBody Employee employee,
			@RequestParam("flag") String flagUpdate) {
		LOGGER.debug("updateEmployee()-start: " + employee + ",flag: " + flagUpdate);
		String responseCode = "";
		ResponseEntity<String> returnEntity = null;
		
		try {
			if (employee != null && flagUpdate != null && !flagUpdate.isEmpty()) {
				this.employeeService.updateEmployee(employee, flagUpdate);
				returnEntity = new ResponseEntity<>("", HttpStatus.OK);
			} else {
				responseCode = "Employee/flagUpdate parameters are null.";
				returnEntity = new ResponseEntity<String>(responseCode, HttpStatus.NO_CONTENT);
			}
			
		} catch (EmployeeServiceException e) {
			LOGGER.error("Failed to update employee...");
			responseCode = "Failed to save to database.";
			returnEntity = new ResponseEntity<String>(responseCode, HttpStatus.NOT_ACCEPTABLE);
		}
		
		return returnEntity;
	}
	
	/**
	 * Deletes the employee information.
	 * 
	 * @param employeeId employee id
	 * @return ResponseEntity object reference
	 */
	@RequestMapping(value="/delete.do", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmployee(@RequestParam(value="employeeId") long employeeId) {
		LOGGER.debug("deleteEmployee()-start: " + employeeId);
		String responseCode = "";
		ResponseEntity<String> returnEntity = null;

		try {
			if (employeeId != 0) {
				this.employeeService.deleteEmployee(employeeId);
				returnEntity = new ResponseEntity<>("", HttpStatus.OK);
			} else {
				responseCode = "Employee id parameter is null.";
				returnEntity = new ResponseEntity<String>(responseCode, HttpStatus.NO_CONTENT);
			}
			
		} catch (EmployeeServiceException e) {
			LOGGER.error("Failed to delete employee...");
			responseCode = "Failed to delete employee..";
			returnEntity = new ResponseEntity<String>(responseCode, HttpStatus.NOT_ACCEPTABLE);
		}
		
		LOGGER.debug("deleteEmployee()-end");
		return returnEntity;
	}
}
