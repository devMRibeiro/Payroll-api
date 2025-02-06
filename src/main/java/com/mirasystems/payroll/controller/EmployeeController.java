package com.mirasystems.payroll.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mirasystems.payroll.model.Employee;
import com.mirasystems.payroll.service.EmployeeService;

@RestController
public class EmployeeController {
	
	private final EmployeeService service;
	
	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all() {
	  return service.getAll();
	}

	@PostMapping("/employees/register")
	public ResponseEntity<?> newEmployee(@RequestBody Employee employee) {

		EntityModel<Employee> entityModel = service.newEmployee(employee);

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Integer id) {
		return service.getOne(id);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) {

		EntityModel<Employee> entityModel = service.replace(newEmployee, id);

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}