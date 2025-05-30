package com.lmlasmo.shrul.infra.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.lmlasmo.shrul.dto.exception.ExceptionDTO;
import com.lmlasmo.shrul.infra.exception.DestinationNotFoundException;
import com.lmlasmo.shrul.infra.exception.EmailConfirmationException;
import com.lmlasmo.shrul.infra.exception.InvalidPasswordException;
import com.lmlasmo.shrul.infra.exception.SystemFailedException;
import com.lmlasmo.shrul.infra.exception.UnchangedFieldException;
import com.lmlasmo.shrul.infra.exception.util.ExceptionDTOFactory;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@ResponseBody
@Order(0)
public class ApplicationExceptionHandler {

	@ExceptionHandler(exception = {EntityExistsException.class, UnchangedFieldException.class})
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ExceptionDTO handleConflictExceptions(Exception exception, HttpServletRequest request){
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.CONFLICT, request, exception);
	}
	
	@ExceptionHandler(exception = {NoHandlerFoundException.class, NoResourceFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)	
	public Void handleNotFoundExceptions(Exception exception) {
		return null;
	}

	@ExceptionHandler(exception = {EmptyResultDataAccessException.class, EntityNotFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionDTO handleNotFoundExceptions(Exception exception, HttpServletRequest request){
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.NOT_FOUND, request, exception);
	}

	@ExceptionHandler(DestinationNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Void handleNotFoundExceptions(DestinationNotFoundException exception){
		return null;
	}

	@ExceptionHandler(SystemFailedException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDTO handleInternalServerExceptions(SystemFailedException exception, HttpServletRequest request) {
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, request, exception.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public ExceptionDTO handleInternalServerExceptions(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.METHOD_NOT_ALLOWED, request, exception);
	}

	@ExceptionHandler(exception = {EmailConfirmationException.class, InvalidPasswordException.class})
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ExceptionDTO handleForbiddenExceptions(Exception exception, HttpServletRequest request){
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.FORBIDDEN, request, exception);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ExceptionDTO handleUnsupportedExceptions(HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
		String message = "Media type not supported. Use 'application/json' in the Content-Type.";
		return ExceptionDTOFactory.getExceptionDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, message);
	}

}
