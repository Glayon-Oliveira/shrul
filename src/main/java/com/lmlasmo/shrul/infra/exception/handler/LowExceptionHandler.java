package com.lmlasmo.shrul.infra.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
@Order(Ordered.LOWEST_PRECEDENCE)
public class LowExceptionHandler {

}
