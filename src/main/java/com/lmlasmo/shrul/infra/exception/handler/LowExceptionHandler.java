package com.lmlasmo.shrul.infra.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lmlasmo.shrul.dto.exception.ExceptionDTO;
import com.lmlasmo.shrul.infra.exception.util.ExceptionDTOFactory;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@ResponseBody
@Order(Ordered.LOWEST_PRECEDENCE)
public class LowExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDTO handleInternalServerExceptions(Exception exception, HttpServletRequest request) {
		exception.printStackTrace();
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
