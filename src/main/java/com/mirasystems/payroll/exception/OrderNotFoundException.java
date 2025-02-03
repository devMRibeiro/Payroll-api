package com.mirasystems.payroll.exception;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(Integer id) {
		super("Order with ID " + id + " not found");
	}
}