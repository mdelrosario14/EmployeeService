package com.empservc.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;
import com.empservc.service.EmployeeService;



/**
 * This class serves as the controller of the employee service module.
 * @author Mardolfh del Rosario
 * 0 - failed to save to db;
 * 1 - saved successfully
 * 2 - employee already exist
 *
 */
@Controller
@RequestMapping("/emp")
public class EmployeeServiceController {
	@Autowired
	EmployeeService employeeService;
	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceController.class);
	
	@RequestMapping(value="/register.do", method = RequestMethod.POST)
	@ResponseBody
	public String registerNewEmployee(@RequestBody Employee employee, HttpServletResponse response) {
		LOGGER.debug("registerNewEmployee()-start: " + employee);
		String responseCode = "1";
		
		try {
			if (employee != null) {
				this.employeeService.registerEmployee(employee);
			} else {
				responseCode = "0:" + "Employee parameters are null.";
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
			
		} catch(EmployeeServiceException e) {
			LOGGER.error("Failed to register employee...");
			if ("0:employee already exists.".equals(e.getMessage())) {
				responseCode = e.getMessage();
			} else {
				responseCode = "0:" + "Failed to save to database.";
			}
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
		
		LOGGER.debug("registerNewEmployee()-end");
		return responseCode;
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateEmployeeInfo(@RequestBody Employee employee,
			@RequestParam("flag") String flagUpdate, HttpServletResponse response) {
		LOGGER.debug("updateEmployee()-start: " + employee + ",flag: " + flagUpdate);
		String responseCode = "1";
		
		try {
			if (employee != null && flagUpdate != null && !flagUpdate.isEmpty()) {
				this.employeeService.updateEmployee(employee, flagUpdate);
			} else {
				responseCode = "0:" + "Employee/flagUpdate parameters are null.";
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
			
		} catch(EmployeeServiceException e) {
			LOGGER.error("Failed to update employee...");
			responseCode = "0:" + "Failed to save to database.";
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
		
		return responseCode;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteEmployee(@RequestParam(value="employeeId") long employeeId, HttpServletResponse response) {
		LOGGER.debug("deleteEmployee()-start: " + employeeId);
		String responseCode = "1";

		try {
			if (employeeId != 0) {
				this.employeeService.deleteEmployee(employeeId);
			} else {
				responseCode = "0:" + "Employee id parameter is null.";
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
			
		} catch(EmployeeServiceException e) {
			LOGGER.error("Failed to delete employee...");
			responseCode = "0:" + "Failed to delete employee..";
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
		
		LOGGER.debug("deleteEmployee()-end");
		return responseCode;
	}
}
