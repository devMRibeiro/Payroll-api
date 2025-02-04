package com.mirasystems.payroll.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.mirasystems.payroll.assembler.EmployeeModelAssembler;
import com.mirasystems.payroll.controller.EmployeeController;
import com.mirasystems.payroll.model.Employee;
import com.mirasystems.payroll.repository.EmployeeRepository;

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

		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
}