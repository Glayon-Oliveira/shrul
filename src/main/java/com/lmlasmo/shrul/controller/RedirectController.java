package com.lmlasmo.shrul.controller;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.lmlasmo.shrul.dto.model.UrlAccessDTO;
import com.lmlasmo.shrul.service.LinkService;
import com.lmlasmo.shrul.service.UrlAccessService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class RedirectController {

	private LinkService service;
	private UrlAccessService accessService;

	@Autowired
	public RedirectController(LinkService service, UrlAccessService accessService) {
		this.service = service;
		this.accessService = accessService;
	}
	
	@RequestMapping("/**")
	public ResponseEntity<Object> notFound(){		
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	public RedirectView redirect(@PathVariable String id, HttpServletRequest request) throws UnknownHostException{

		String destine = service.getDestination(id.toLowerCase());

		accessService.save(new UrlAccessDTO(id, request));

		return new RedirectView(destine);
	}

	@GetMapping("/{prefix}/{id}")
	public RedirectView redirect(@PathVariable String id, @PathVariable String prefix, HttpServletRequest request) throws UnknownHostException{

		String destine = service.getDestination(id.toLowerCase(), prefix.toLowerCase());

		accessService.save(new UrlAccessDTO(id, request));

		return new RedirectView(destine);
	}

}
