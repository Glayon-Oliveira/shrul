package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.model.PrefixDTO;
import com.lmlasmo.shrul.dto.register.PrefixUpdateDTO;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.service.PrefixService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prefix")
public class PrefixController {

	private PrefixService service;
	
	@Autowired
	public PrefixController(PrefixService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<PrefixDTO> register(@RequestBody @Valid PrefixDTO prefix){
		
		prefix = service.save(prefix);
				
		return ResponseEntity.ok(prefix);				
	}
	
	@PutMapping
	public ResponseEntity<PrefixDTO> update(@RequestBody @Valid PrefixUpdateDTO update){
		
		PrefixDTO prefix = service.update(update);
		
		if(prefix != null) {
			return ResponseEntity.ok(prefix);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam BigInteger id){
		
		boolean deleted = service.delete(id);
		
		if(deleted) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<Page<PrefixDTO>> findById(Pageable pageable){
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<PrefixDTO> prefixPage = service.findByUser(user.getId(), pageable);
		
		return ResponseEntity.ok(prefixPage);		
	}
	
}