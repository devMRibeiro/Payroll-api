package com.mirasystems.payroll.service;

import com.mirasystems.payroll.exception.OrderNotFoundException;
import com.mirasystems.payroll.model.Order;
import com.mirasystems.payroll.repository.OrderRepository;

public class OrderService {

	private OrderRepository repository;

	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}

	public Order findOrderById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
	}
}