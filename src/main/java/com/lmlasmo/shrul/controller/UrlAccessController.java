package com.lmlasmo.shrul.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.UrlAccessDTO;
import com.lmlasmo.shrul.infra.security.AuthenticatedUser;
import com.lmlasmo.shrul.service.UrlAccessService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/url_access")
@ResponseStatus(code = HttpStatus.OK)
public class UrlAccessController {

	private UrlAccessService service;

	@GetMapping("/week")
	@ResponseBody
	public Page<UrlAccessDTO> findByLastWeek(Pageable pageable){
		return service.findByLastWeek(AuthenticatedUser.getUserId(), pageable);
	}

	@GetMapping("/month")
	@ResponseBody
	public Page<UrlAccessDTO> findByLastMonth(Pageable pageable){
		return service.findByLastMonth(AuthenticatedUser.getUserId(), pageable);
	}

	@GetMapping
	@ResponseBody
	public Page<UrlAccessDTO> findAll(Pageable pageable){
		return service.findAll(AuthenticatedUser.getUserId(), pageable);
	}

}
