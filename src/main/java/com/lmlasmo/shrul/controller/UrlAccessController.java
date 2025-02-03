package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.model.UrlAccessDTO;
import com.lmlasmo.shrul.service.AccessVerifyService;
import com.lmlasmo.shrul.service.UrlAccessService;

@RestController
@RequestMapping("/api/url_access")
public class UrlAccessController {

	private UrlAccessService service;

	@Autowired
	public UrlAccessController(UrlAccessService service) {
		this.service = service;
	}

	@GetMapping("/week")
	public ResponseEntity<Page<UrlAccessDTO>> findByLastWeek(Pageable pageable){

		BigInteger user_id = AccessVerifyService.getUserId();

		Page<UrlAccessDTO> page = service.findByLastWeek(user_id, pageable);

		return ResponseEntity.ok(page);
	}

	@GetMapping("/month")
	public ResponseEntity<Page<UrlAccessDTO>> findByLastMonth(Pageable pageable){

		BigInteger user_id = AccessVerifyService.getUserId();

		Page<UrlAccessDTO> page = service.findByLastMonth(user_id, pageable);

		return ResponseEntity.ok(page);
	}

	@GetMapping("/all")
	public ResponseEntity<Page<UrlAccessDTO>> findAll(Pageable pageable){

		BigInteger user_id = AccessVerifyService.getUserId();

		Page<UrlAccessDTO> page = service.findAll(user_id, pageable);

		return ResponseEntity.ok(page);
	}

}
