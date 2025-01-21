package com.mirasystems.payroll.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4331656123393763680L;

	public EmployeeNotFoundException(Integer id) {
		super("Could not find employee " + id);
	}
}