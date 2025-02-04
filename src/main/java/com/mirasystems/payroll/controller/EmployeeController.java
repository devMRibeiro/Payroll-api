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

import com.mirasystems.payroll.assembler.EmployeeModelAssembler;
import com.mirasystems.payroll.exception.EmployeeNotFoundException;
import com.mirasystems.payroll.model.Employee;
import com.mirasystems.payroll.repository.EmployeeRepository;
import com.mirasystems.payroll.service.EmployeeService;

@RestController
public class EmployeeController {
	
	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;
	private final EmployeeService service;
	
	public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler, EmployeeService service) {
		this.repository = repository;
		this.assembler = assembler;
		this.service = service;
	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all() {
	  return service.getAll();
	}

	@PostMapping("/employees/register")
	public ResponseEntity<?> newEmployee(@RequestBody Employee employee) {
		
		EntityModel<Employee> entityModel = assembler.toModel(repository.save(employee));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Integer id) {
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
		
		return assembler.toModel(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) {

		Employee updateEmployee = repository.findById(id)
				.map(employee -> {
					employee.setName(newEmployee.getName());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
				}).orElseGet(() -> {
					return repository.save(newEmployee);
		});
		
		EntityModel<Employee> entityModel = assembler.toModel(updateEmployee);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}