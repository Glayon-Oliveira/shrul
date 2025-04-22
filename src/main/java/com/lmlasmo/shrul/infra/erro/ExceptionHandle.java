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
		return new ExceptionDTO(409, HttpStatus.CONFLICT.getReasonPhrase(), new ErrorMessageDTO(exception.getMessage()));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionDTO exception(EntityNotFoundException exception){
		return new ExceptionDTO(404, HttpStatus.NOT_FOUND.getReasonPhrase(), new ErrorMessageDTO(exception.getMessage()));
	}

	@ExceptionHandler(GenericException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO exception(GenericException exception){
		return new ExceptionDTO(400, HttpStatus.BAD_REQUEST.getReasonPhrase(), new ErrorMessageDTO(exception.getMessage()));
	}
	
	@ExceptionHandler(DestinationNotFoundException.class)	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Void exception(DestinationNotFoundException exception){				
		return null;
	}	

	@ExceptionHandler(EmailConfirmationException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ExceptionDTO exception(EmailConfirmationException exception){
		return new ExceptionDTO(403, HttpStatus.FORBIDDEN.getReasonPhrase(), new ErrorMessageDTO(exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO exception(MethodArgumentNotValidException exception){
		ErrorMessageDTO[] errors = exception.getFieldErrors().stream()
															 .map(e -> new ErrorMessageDTO(e.getField(), e.getDefaultMessage()))
															 .toList()
															 .toArray(ErrorMessageDTO[]::new);

		return new ExceptionDTO(400, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO exception(HttpMessageNotReadableException exception){
		String message = "Required request body is missing";

		if(exception.getMessage().contains("DTO")) message += ": JSON";		

		return new ExceptionDTO(400, HttpStatus.BAD_REQUEST.getReasonPhrase(), new ErrorMessageDTO(message));
	}

}
