package com.lmlasmo.shrul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.lmlasmo.shrul.service.LinkService;

@RestController
public class RedirectController {

	private LinkService service;

	@Autowired
	public RedirectController(LinkService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Object redirect(@PathVariable String id){
		
		String destine = service.getDestination(id);		
	
		return (destine != null) ? new RedirectView(destine) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{prefix}/{id}")
	public Object redirect(@PathVariable String id, @PathVariable String prefix){
		
		String destine = service.getDestination(id, prefix);
		
		return (destine != null) ? new RedirectView(destine) : ResponseEntity.notFound().build();
	}

}
