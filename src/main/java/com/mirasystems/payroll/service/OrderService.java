package com.mirasystems.payroll.service;

import com.mirasystems.payroll.enums.EnumStatus;
import com.mirasystems.payroll.exception.InvalidOrderOperationException;
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