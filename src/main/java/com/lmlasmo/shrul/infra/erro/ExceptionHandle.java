package com.lmlasmo.shrul.infra.erro;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lmlasmo.shrul.dto.error.ErrorMessageDTO;
import com.lmlasmo.shrul.dto.error.ExceptionDTO;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ExceptionHandle {	
	
	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)	
	public ExceptionDTO exception(EntityExistsException exception){
					
		return new ExceptionDTO(HttpStatus.CONFLICT, exception);		
	}
	
	@ExceptionHandler(EntityNotFoundException.class)	
	public ExceptionDTO exception(EntityNotFoundException exception){
					
		return new ExceptionDTO(HttpStatus.BAD_REQUEST, exception);				
	}
	
	@ExceptionHandler(GenericException.class)	
	public ExceptionDTO exception(GenericException exception){
		
		return new ExceptionDTO(HttpStatus.BAD_REQUEST, exception);				
	}
	
	@ExceptionHandler(DestinationNotFoundException.class)	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionDTO exception(DestinationNotFoundException exception){				
		return null;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)	
	public ExceptionDTO exception(MethodArgumentNotValidException exception){				
		
		ErrorMessageDTO[] errors = exception.getFieldErrors().stream()
															 .map(e -> new ErrorMessageDTO(e.getField(), e.getDefaultMessage()))
															 .toList()
															 .toArray(ErrorMessageDTO[]::new);
		
		return new ExceptionDTO(HttpStatus.BAD_REQUEST, errors);		
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)	
	public ExceptionDTO exception(HttpMessageNotReadableException exception){		
		
		String message = "Required request body is missing";
		
		if(exception.getMessage().contains("DTO")) {
			message += ": JSON";
		}
		
		return new ExceptionDTO(HttpStatus.BAD_REQUEST, new ErrorMessageDTO(exception.getCause(), message));							
	}			

}
