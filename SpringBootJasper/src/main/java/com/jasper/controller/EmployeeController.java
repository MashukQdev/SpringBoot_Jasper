package com.jasper.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jasper.entity.Employee;
import com.jasper.service.EmployeeService;
import net.sf.jasperreports.engine.JRException;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployee() {
		return service.getAllEmployee();
	}
	
	@GetMapping("/report/{format}")
	public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
		return service.exportReport(format);
	}
	
	@GetMapping("/dynamicreport")
	public String dynamicReport() {
		return service.dynamicExport();
	}
}
