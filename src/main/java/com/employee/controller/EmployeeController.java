package com.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entities.Employee;
import com.employee.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository repo;

	HashMap<Integer, Employee> cache = new HashMap<>();

	// API's for CRUD Operation

	// insert employee into cache/DB

	@PostMapping("/postEmployee")
	public Employee insertEmployee(@RequestBody Employee employee) {

		// inserting employee in DB

		Employee employee2 = repo.save(employee);

		// inserting employee in cache

		cache.put(employee.employeeId, employee);

		System.out.println(cache.entrySet().iterator().next().getValue().toString());
//
		return cache.get(employee.employeeId);

	//	return employee2;

	}

	// Get Employee from chache

	@GetMapping("/getEmployee/{var}")
	public Optional<Employee> getEmployee(@PathVariable int var) {

		// get employee from DB

		// Employee byId = repo.getById(var);
		Optional<Employee> findById = repo.findById(var);

		// Getting employee from cache
//		Employee employee = cache.get(var);
//		System.out.println(employee);
//		return employee;

		return findById;

	}

	// Update Employee to cache

	@PutMapping("/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee employee) {

		Employee employee2 = repo.findById(employee.getEmployeeId()).get();

		System.out.println(employee2);

		employee2.setEmployeeId(employee.getEmployeeId());

		employee2.setEmployeeName(employee.getEmployeeName());

		employee2.setEmployeeCity(employee.getEmployeeCity());

		employee2.setEmployeeSalary(employee.getEmployeeSalary());

		return repo.save(employee2);

//		Integer employeeId = employee.employeeId;
//
//		cache.put(employeeId, employee);

		/*
		 * By Using Getter and setters
		 */
//Updating employee in cache
//		Employee currentEmployee = cache.get(employee.employeeId);
//		currentEmployee.setEmployeeName(employee.employeeName);
//		currentEmployee.setEmployeeCity(employee.employeeCity);
//		currentEmployee.setEmployeeSalary(employee.getEmployeeSalary());
//		return currentEmployee;

	}

	// Delete Employee from cache

	@SuppressWarnings("deprecation")
	@DeleteMapping("/deleteEmployee/{var}")
	public String deleteEmployee(@PathVariable int var) {

		// delete employee from DB

		Employee byId = repo.getById(var);

		if (byId != null) {

			repo.delete(byId);
			return "Delete success";
		} else {

			return "Object Not found";
		}

		// delete employee from cache
//		Employee employee = cache.remove(var);
//		return employee;

	}

	// Get All Employees

	@GetMapping("/getAllEmployees")
	public List<Employee> getAllEmployees() {

		// get All Empployees from DB

		return repo.findAll();

		// get All Employees from cache
		// List<Employee> list = new ArrayList<>();

//		Iterator<Entry<Integer, Employee>> itr = cache.entrySet().iterator();
//		while (itr.hasNext()) {
//
//			Employee employee = cache.get(itr.next().getKey());
//			list.add(employee);
//
//		}
		// return list;
		// return cache.values().stream().collect(Collectors.toList());

	}

}
