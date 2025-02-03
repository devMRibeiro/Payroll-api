package com.mirasystems.payroll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mirasystems.payroll.util.ProblemDetailBuilder;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final ProblemDetailBuilder problemDetailBuilder;
	
	public GlobalExceptionHandler(ProblemDetailBuilder problemDetailBuilder) {
		this.problemDetailBuilder = problemDetailBuilder;
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ProblemDetail> employeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
		return problemDetailBuilder.build(HttpStatus.NOT_FOUND, request, ex.getMessage(), "EmployeeNotFound");
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ProblemDetail> orderNotFoundException(OrderNotFoundException ex, WebRequest request) {

		return problemDetailBuilder.build(HttpStatus.NOT_FOUND, request, ex.getMessage(), "OrderNotFound");
	}
	
	@ExceptionHandler(InvalidOrderOperationException.class)
  public ResponseEntity<ProblemDetail> invalidOrderOperationException(InvalidOrderOperationException ex, WebRequest request) {

		return problemDetailBuilder.build(HttpStatus.METHOD_NOT_ALLOWED, request, ex.getMessage(), "InvalidOrderOperation");
  }
}