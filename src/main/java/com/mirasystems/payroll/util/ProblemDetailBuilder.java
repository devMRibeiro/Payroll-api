package com.mirasystems.payroll.util;

import java.util.Date;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class ProblemDetailBuilder {

	public ResponseEntity<ProblemDetail> build(HttpStatus httpStatus, WebRequest request, String exceptionMessage, String errorType) {

		ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, exceptionMessage);

		problem.setTitle(httpStatus.getReasonPhrase());
		problem.setProperty("Path", request.getDescription(false));
		problem.setProperty("Timestamp", new Date());
		problem.setProperty("ErroyType", errorType);

		return ResponseEntity
				.status(httpStatus)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(problem);		
	}
}