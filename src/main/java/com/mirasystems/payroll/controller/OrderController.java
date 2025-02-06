package com.mirasystems.payroll.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.mirasystems.payroll.model.Order;
import com.mirasystems.payroll.service.OrderService;

@RestController
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@GetMapping("/orders")
	public CollectionModel<EntityModel<Order>> all() {
		return service.getAll();
	}

	@GetMapping("/orders/{id}")
	public EntityModel<Order> one(@PathVariable Integer id) {
		return service.findById(id);
	}

	@PostMapping("/orders/register")
	public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

		EntityModel<Order> newOrder = service.newOrder(order);

		return ResponseEntity
				.created(linkTo(methodOn(OrderController.class).one(newOrder.getContent().getId())).toUri())
				.body(newOrder);
	}

	@DeleteMapping("/orders/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Integer id) {
		return ResponseEntity.ok(service.cancel(id));
	}

	@PutMapping("/orders/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable Integer id) {
		return ResponseEntity.ok(service.complete(id));
	}
}