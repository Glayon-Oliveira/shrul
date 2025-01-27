package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.lmlasmo.shrul.service.AccessVerifyService;
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

		prefix = service.save(prefix, AccessVerifyService.getUserId());

		return ResponseEntity.ok(prefix);
	}

	@PutMapping
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#update.id)")
	public ResponseEntity<PrefixDTO> update(@RequestBody @Valid PrefixUpdateDTO update){

		PrefixDTO prefix = service.update(update);

		return ResponseEntity.ok(prefix);
	}

	@DeleteMapping
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#id)")
	public ResponseEntity<Object> delete(@RequestParam BigInteger id){

		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/empty")
	public ResponseEntity<PrefixDTO> findEmptyPrefix(){

		PrefixDTO prefix = service.findByEmptyPrefix(AccessVerifyService.getUserId());

		return ResponseEntity.ok(prefix);
	}

	@GetMapping
	public ResponseEntity<Page<PrefixDTO>> findPrefixes(Pageable pageable){

		Page<PrefixDTO> prefixPage = service.findByUser(AccessVerifyService.getUserId(), pageable);

		return ResponseEntity.ok(prefixPage);
	}

}