package com.mirasystems.payroll.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mirasystems.payroll.assembler.OrderModelAssembler;
import com.mirasystems.payroll.model.Order;
import com.mirasystems.payroll.repository.OrderRepository;
import com.mirasystems.payroll.service.OrderService;

@RestController
public class OrderController {

	private final OrderRepository repository;
	private final OrderModelAssembler assembler;
	private final OrderService service;

	public OrderController(OrderRepository repository, OrderModelAssembler assembler, OrderService service) {
		this.repository = repository;
		this.assembler = assembler;
		this.service = service;
	}

	@GetMapping("/orders")
	public CollectionModel<EntityModel<Order>> all() {

		List<EntityModel<Order>> orders = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}

	@GetMapping("/orders/{id}")
	public EntityModel<Order> one(@PathVariable Integer id) {
		return assembler.toModel(service.findOrderById(id));
	}

	@PostMapping("/orders/register")
	public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

		Order newOrder = service.newOrder(order);

		return ResponseEntity
				.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
				.body(assembler.toModel(newOrder));
	}

	@DeleteMapping("/orders/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Integer id) {
		return ResponseEntity.ok(assembler.toModel(service.cancel(id)));
	}

	@PutMapping("/orders/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable Integer id) {
		return ResponseEntity.ok(assembler.toModel(service.complete(id)));
	}
}