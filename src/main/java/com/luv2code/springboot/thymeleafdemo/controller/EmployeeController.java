	package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	// add mapping for "/list"
	@GetMapping("/list")
	public String getEmployees(Model model) {
		
		List<Employee> employees = employeeService.findAll();
		
		// add to the spring model
		model.addAttribute("employees", employees);
		
		return "employees/list-employees";
		
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		
		
		// create model attribute to bind form data
		Employee employee = new Employee();
		
		model.addAttribute("employee", employee);
		
		return "employees/employee-form";
		
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("employee") Employee employee) {
		
		
		// save the employee		
		employeeService.save(employee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam int employeeId, Model model) {
		
		
		// get employee from the service
		Employee employee = employeeService.findById(employeeId);
		
		// set the employee as a model attribute to pre-populate the form		
		model.addAttribute("employee", employee);
		
		// send over to our form
		return "employees/employee-form";
		
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam int employeeId) {
		
		
		// delete the employee		
		employeeService.deleteById(employeeId);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("employeeName") String name, Model model) {
		
		
		// search employees by name (first name or last name)
		List<Employee> employees = employeeService.searchBy(name);
		
		// assign result to model
		model.addAttribute("employees", employees);
		
		return "/employees/list-employees";
		
	}
	
	
}
