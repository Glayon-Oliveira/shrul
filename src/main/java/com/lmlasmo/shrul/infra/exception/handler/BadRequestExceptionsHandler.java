package com.lmlasmo.shrul.infra.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lmlasmo.shrul.dto.error.ErrorMessageDTO;
import com.lmlasmo.shrul.dto.error.ExceptionDTO;
import com.lmlasmo.shrul.infra.exception.ActionFailedException;
import com.lmlasmo.shrul.infra.exception.util.ExceptionDTOFactory;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@ResponseBody
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestExceptionsHandler {
	
	@ExceptionHandler(ActionFailedException.class)	
	public ExceptionDTO handleBadRequestExceptions(ActionFailedException exception, HttpServletRequest request){
		return ExceptionDTOFactory.getExceptionDTO(request, exception);		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)	
	public ExceptionDTO handleBadRequestExceptions(MethodArgumentNotValidException exception, HttpServletRequest request){
		ErrorMessageDTO[] messages = exception.getFieldErrors().stream()
				.map(e -> new ErrorMessageDTO(e.getField(), e.getDefaultMessage()))
				.toArray(ErrorMessageDTO[]::new);
		
		return ExceptionDTOFactory.getExceptionDTO(request, messages);		
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)	
	public ExceptionDTO handleBadRequestExceptions(HttpMessageNotReadableException exception, HttpServletRequest request){
		String message = "The requisition body is malformed, missing, or in invalid form.";
		return ExceptionDTOFactory.getExceptionDTO(request, message);		
	}	

}
