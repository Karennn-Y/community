package com.project.community.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		ErrorResponse errorResponse =
			new ErrorResponse(e.getExceptionCode().getCode(), e.getExceptionCode().getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getExceptionCode().getCode()));
	}
}
