package com.mirasystems.payroll.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.mirasystems.payroll.assembler.EmployeeModelAssembler;
import com.mirasystems.payroll.controller.EmployeeController;
import com.mirasystems.payroll.exception.EmployeeNotFoundException;
import com.mirasystems.payroll.model.Employee;
import com.mirasystems.payroll.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;

	public EmployeeService(EmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	public CollectionModel<EntityModel<Employee>> getAll() {
		List<EntityModel<Employee>> employees = repository.findAll().stream()
	      .map(assembler::toModel)
	      .collect(Collectors.toList());

		return CollectionModel
				.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}

	public EntityModel<Employee> getOne(Integer id) {
		return assembler.toModel(repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id)));
	}

	public EntityModel<Employee> save(Employee employee) {
		return assembler.toModel(repository.save(employee));
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public EntityModel<Employee> replace(Employee newEmployee, Integer id) {

		Employee updateEmployee = repository.findById(id)
				.map(employee -> {
					employee.setName(newEmployee.getName());
					employee.setRole(newEmployee.getRole());

					return repository.save(employee);

				}).orElseThrow(() -> new EmployeeNotFoundException(id));

		return assembler.toModel(updateEmployee);
	}
}