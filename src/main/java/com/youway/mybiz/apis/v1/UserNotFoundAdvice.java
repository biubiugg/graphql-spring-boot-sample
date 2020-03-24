package com.youway.mybiz.apis.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.youway.mybiz.exception.BookNotFoundException;
import com.youway.mybiz.exception.UserNotFoundException;

@ControllerAdvice
public class UserNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(UserNotFoundException ex) {
		return ex.getMessage();
	}
}
