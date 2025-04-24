package com.lmlasmo.shrul.infra.exception.util;

import org.springframework.http.HttpStatus;

import com.lmlasmo.shrul.dto.error.ErrorMessageDTO;
import com.lmlasmo.shrul.dto.error.ExceptionDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ExceptionDTOFactory {

	public static ExceptionDTO getExceptionDTO(HttpStatus status, HttpServletRequest request, Exception exception) {
		return getExceptionDTO(status, request, exception.getMessage());
	}
	
	public static ExceptionDTO getExceptionDTO(HttpStatus status, HttpServletRequest request, String message) {
		return new ExceptionDTO(status, request.getRequestURI(), new ErrorMessageDTO(message));
	}
	
	public static ExceptionDTO getExceptionDTO(HttpStatus status, HttpServletRequest request, ErrorMessageDTO... messages) {
		return new ExceptionDTO(status, request.getRequestURI(), messages);
	}
	
	public static ExceptionDTO getExceptionDTO(HttpServletRequest request, Exception exception) {
		return getExceptionDTO(request, exception.getMessage());
	}
	
	public static ExceptionDTO getExceptionDTO(HttpServletRequest request, String message) {
		return new ExceptionDTO(request.getRequestURI(), new ErrorMessageDTO(message));
	}
	
	public static ExceptionDTO getExceptionDTO(HttpServletRequest request, ErrorMessageDTO... messages) {
		return new ExceptionDTO(request.getRequestURI(), messages);
	}
	
	public static ExceptionDTO getExceptionDTO(HttpStatus status, HttpServletRequest request) {
		return new ExceptionDTO(status, request.getRequestURI());
	}
	
}
