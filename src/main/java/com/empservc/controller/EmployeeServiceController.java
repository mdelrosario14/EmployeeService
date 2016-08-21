package com.empservc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empservc.exception.EmployeeServiceException;
import com.empservc.model.Employee;
import com.empservc.service.EmployeeService;



/**
 * This class serves as the controller of the employee service module.
 * @author Mardolfh del Rosario
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
			responseCode = "0:" + "Failed to save to database.";
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
		
		LOGGER.debug("registerNewEmployee()-end");
		return responseCode;
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ResponseBody
	public String testonly(ModelMap model) {
		int responseCode = 1;
		List<Employee> allEmployees = this.employeeService.getAllEmployees();
		if (allEmployees != null && !allEmployees.isEmpty()) {
			responseCode = 1;
		} else {
			responseCode = 0;
		}
		System.out.println("OK.");
		return "" + responseCode;
	}
	
	
	
}
