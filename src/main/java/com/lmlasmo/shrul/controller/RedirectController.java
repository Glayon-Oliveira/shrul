package com.lmlasmo.shrul.controller;

import java.net.UnknownHostException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.lmlasmo.shrul.service.LinkService;
import com.lmlasmo.shrul.service.UrlAccessService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class RedirectController {

	private LinkService service;
	private UrlAccessService accessService;

	@RequestMapping("/**")
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Void notFound(){
		return null;
	}

	@GetMapping("/{id}")
	public RedirectView redirect(@PathVariable String id, HttpServletRequest request) throws UnknownHostException{
		String destine = service.getDestination(id.toLowerCase());
		accessService.save(id, request);
		RedirectView redirect = new RedirectView(destine);
		redirect.setStatusCode(HttpStatusCode.valueOf(303));
		return redirect;
	}

	@GetMapping("/{prefix}/{id}")
	public RedirectView redirect(@PathVariable String id, @PathVariable String prefix, HttpServletRequest request) throws UnknownHostException{
		String destine = service.getDestination(id.toLowerCase(), prefix.toLowerCase());
		accessService.save(id, request);
		RedirectView redirect = new RedirectView(destine);
		redirect.setStatusCode(HttpStatusCode.valueOf(303));
		return redirect;
	}

}
