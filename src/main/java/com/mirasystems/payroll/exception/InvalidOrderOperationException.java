package com.mirasystems.payroll.exception;

import com.mirasystems.payroll.enums.EnumStatus;

public class InvalidOrderOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidOrderOperationException(EnumStatus status) {
		super("Invalid operation for the current order status: " + status);
	}
}