package com.mirasystems.payroll.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.mirasystems.payroll.assembler.OrderModelAssembler;
import com.mirasystems.payroll.controller.OrderController;
import com.mirasystems.payroll.enums.EnumStatus;
import com.mirasystems.payroll.exception.InvalidOrderOperationException;
import com.mirasystems.payroll.exception.OrderNotFoundException;
import com.mirasystems.payroll.model.Order;
import com.mirasystems.payroll.repository.OrderRepository;

public class OrderService {

	private final OrderRepository repository;
	private final OrderModelAssembler assembler;

	public OrderService(OrderRepository repository, OrderModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	public Order findOrderById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
	}

	public CollectionModel<EntityModel<Order>> getAll() {
		List<EntityModel<Order>> orders = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

		return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}
	
	public Order newOrder(Order order) {
		order.setStatus(EnumStatus.IN_PROGRESS);
		return repository.save(order);
	}
	
	public Order cancel(Integer id) {

		Order order = findOrderById(id);

		if (order.getStatus() != EnumStatus.IN_PROGRESS)
			throw new InvalidOrderOperationException(order.getStatus());
	
		order.setStatus(EnumStatus.CANCELLED);
    return repository.save(order);
	}
	
	public Order complete(Integer id) {
		Order order = findOrderById(id);

		if (order.getStatus() != EnumStatus.IN_PROGRESS)
			throw new InvalidOrderOperationException(order.getStatus());
	
		order.setStatus(EnumStatus.COMPLETED);
    return repository.save(order);
	}
}