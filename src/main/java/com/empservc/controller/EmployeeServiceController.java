package com.empservc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value="/register.do", method = RequestMethod.POST)
	@ResponseBody
	public int registerNewEmployee(@RequestBody Employee employee) {
		int responseCode = 1;
		
		if (employee != null) {
			System.out.println("Received request: " + employee);
		} else {
			responseCode = 0;
		}
		
		
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
