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

import com.lmlasmo.shrul.dto.model.LinkDTO;
import com.lmlasmo.shrul.dto.register.LinkUpdateDTO;
import com.lmlasmo.shrul.service.AccessVerifyService;
import com.lmlasmo.shrul.service.LinkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/link")
public class LinkController {

	private LinkService service;

	@Autowired
	public LinkController(LinkService service) {
		this.service = service;
	}

	@PostMapping
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#link.prefix)")
	public ResponseEntity<LinkDTO> register(@RequestBody @Valid LinkDTO link){

		LinkDTO dto = service.save(link);

		return ResponseEntity.ok(dto);
	}

	@PutMapping
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#update.prefix)")
	public ResponseEntity<LinkDTO> update(@RequestBody @Valid LinkUpdateDTO update){

		LinkDTO link = service.update(update);

		return ResponseEntity.ok(link);
	}

	@DeleteMapping
	@PreAuthorize("@accessVerifyService.verifyLinkAccess(#id)")
	public ResponseEntity<Object> delete(@RequestParam String id){

		service.delete(id.toLowerCase());

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Page<LinkDTO>> findByUser(Pageable pageable){

		Page<LinkDTO> linkPage = service.findByUser(AccessVerifyService.getUserId(), pageable);

		return ResponseEntity.ok(linkPage);
	}

	@GetMapping(params = "prefix")
	@PreAuthorize("@accessVerifyService.verifyPrefixAccess(#id)")
	public ResponseEntity<Page<LinkDTO>> findByPrefix(@RequestParam("prefix") BigInteger id, Pageable pageable){

		Page<LinkDTO> linkPage = service.findByPrefix(id, pageable);

		return ResponseEntity.ok(linkPage);
	}

}
