package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.LinkDTO;
import com.lmlasmo.shrul.service.LinkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/link")
public class LinkController {

	private LinkService service;
	
	public LinkController(LinkService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<LinkDTO> register(@RequestBody @Valid LinkDTO link){
		
		LinkDTO dto = service.save(link);
		
		return ResponseEntity.ok(dto);		
	}	
	
	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam String id){
		
		boolean deleted = service.delete(id.toLowerCase());	
		
		return (deleted) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();		
	}
	
	@GetMapping
	public ResponseEntity<Page<LinkDTO>> findByPrefix(@RequestParam("prefix") BigInteger id, Pageable pageable){
		
		Page<LinkDTO> linkPage = service.findByPrefix(id, pageable);
				
		return ResponseEntity.ok(linkPage);
	}	
	
}
