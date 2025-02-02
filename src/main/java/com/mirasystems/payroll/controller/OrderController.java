package com.mirasystems.payroll.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mirasystems.payroll.assembler.OrderModelAssembler;
import com.mirasystems.payroll.enums.EnumStatus;
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
		Order order = service.findOrderById(id);

		return assembler.toModel(order);
	}

	@PostMapping("/orders/register")
	public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

		order.setStatus(EnumStatus.IN_PROGRESS);
		Order newOrder = repository.save(order);

		return ResponseEntity
				.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
				.body(assembler.toModel(newOrder));
	}

	@DeleteMapping("/orders/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Integer id) {

		Order order = service.findOrderById(id);

		if (order.getStatus() == EnumStatus.IN_PROGRESS) {
			order.setStatus(EnumStatus.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(repository.save(order)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
	}

	@PutMapping("/orders/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable Integer id) {
		
		Order order = service.findOrderById(id);
		
		if (order.getStatus() == EnumStatus.IN_PROGRESS) {
			order.setStatus(EnumStatus.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(repository.save(order)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
	}
}