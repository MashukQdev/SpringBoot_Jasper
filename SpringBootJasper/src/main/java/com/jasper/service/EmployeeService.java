package com.jasper.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.jasper.entity.Employee;

import net.sf.jasperreports.engine.JRException;

public interface EmployeeService {

	public List<Employee> getAllEmployee();
	
	public String exportReport(String reportFormat);
	
	public String dynamicExport();
}
