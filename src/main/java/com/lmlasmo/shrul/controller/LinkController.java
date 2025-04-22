package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.model.LinkDTO;
import com.lmlasmo.shrul.dto.register.LinkUpdateDTO;
import com.lmlasmo.shrul.service.AccessVerifyService;
import com.lmlasmo.shrul.service.LinkService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/link")
@ResponseStatus(code = HttpStatus.OK)
public class LinkController {

	private LinkService service;

	@PostMapping
	@ResponseBody	
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#link.prefix)")
	public LinkDTO register(@RequestBody @Valid LinkDTO link){
		return service.save(link);		
	}

	@PutMapping
	@ResponseBody
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#update.prefix)")
	public LinkDTO update(@RequestBody @Valid LinkUpdateDTO update){
		return service.update(update);
	}

	@DeleteMapping
	@PreAuthorize("@accessVerifyService.verifyLinkAccess(#id)")
	public Void delete(@RequestParam String id){
		service.delete(id.toLowerCase());
		return null;
	}

	@GetMapping
	@ResponseBody
	public Page<LinkDTO> findByUser(Pageable pageable){
		return service.findByUser(AccessVerifyService.getUserId(), pageable);		
	}

	@GetMapping(params = "prefix")
	@ResponseBody
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#id)")
	public Page<LinkDTO> findByPrefix(@RequestParam("prefix") BigInteger id, Pageable pageable){
		return service.findByPrefix(id, pageable);		
	}

}
