package com.jasper.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.jasper.entity.Employee;
import com.jasper.entity.Student;
import com.jasper.repository.EmployeeRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Override
	public List<Employee> getAllEmployee() {
		return repository.findAll();
	}

	@Override
	public String exportReport(String reportFormat) {
		List<Employee> employees = repository.findAll();
		String path = "D:\\Jasper Reports";
		try {
			File file = ResourceUtils.getFile("classpath:employees.jrxml");
			
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("createdBy", "Mashuk");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			
			if(reportFormat.equalsIgnoreCase("html")) {
				JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\employees.html");
				return "HTML report is generated at location: " + path;
			}
			if(reportFormat.equalsIgnoreCase("pdf")) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
				return "PDF report is generated at location: " + path;
			}
		} catch (Exception e) {
			return "Error creating report.";
		}
		return "";
	}

	@Override
	public String dynamicExport() {
		List<Student> students = new ArrayList<>();
		String path = "D:\\Jasper Reports";
		
		students.add(addStudentData(1, "Mashuk", "Patel", 22, "2001-12-29"));
		students.add(addStudentData(2, "Rutvik", "Patel", 22, "2001-11-13"));
		students.add(addStudentData(3, "Saunak", "Patel", 21, "2002-02-07"));
		students.add(addStudentData(4, "Rohit", "Vanzara", 22, "2001-12-13"));
		students.add(addStudentData(5, "Saurav", "Ninama", 23, "2000-09-12"));
		
		try {
			File file = ResourceUtils.getFile("classpath:Student.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("created By", "Mashuk Patel");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");
			
			return "PDF report generated to location: " + path;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "PDF report not generated.";
		}
	}
	
	private Student addStudentData(int id, String firstName, String lastName, int age, String dateOfBirth) {
		Student student = new Student();
		
		student.setId(id);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setAge(age);
		student.setDateOfBirth(Date.valueOf(dateOfBirth));
		
		return student;
	}
	
}
