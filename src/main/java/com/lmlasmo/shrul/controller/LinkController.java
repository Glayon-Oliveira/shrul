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

import com.lmlasmo.shrul.dto.LinkDTO;
import com.lmlasmo.shrul.dto.register.RegisterLinkDTO;
import com.lmlasmo.shrul.dto.update.LinkUpdateDTO;
import com.lmlasmo.shrul.infra.security.AuthenticatedUser;
import com.lmlasmo.shrul.service.LinkService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/link")
@ResponseStatus(code = HttpStatus.OK)
public class LinkController {

	private LinkService service;

	@PostMapping
	@ResponseBody
	@PreAuthorize("@prefixService.existsByIdAndAuth(#link.prefixId)")
	public LinkDTO register(@RequestBody @Valid RegisterLinkDTO link){
		return service.save(link);
	}

	@PutMapping
	@ResponseBody
	@PreAuthorize("@prefixService.existsByIdAndAuth(#update.prefixId)")
	public LinkDTO update(@RequestBody @Valid LinkUpdateDTO update){
		return service.update(update);
	}

	@DeleteMapping
	@PreAuthorize("@linkService.existsByIdAndAuth(#id)")
	public Void delete(@RequestParam @NotBlank @Size(min = 10, max = 10) String id){
		service.delete(id.toLowerCase());
		return null;
	}

	@GetMapping
	@ResponseBody
	public Page<LinkDTO> findByUser(Pageable pageable){
		return service.findByUser(AuthenticatedUser.getUserId(), pageable);
	}

	@GetMapping(params = "prefix")
	@ResponseBody
	@PreAuthorize("@prefixService.existsByIdAndAuth(#id)")
	public Page<LinkDTO> findByPrefix(@RequestParam("prefix") BigInteger id, Pageable pageable){
		return service.findByPrefix(id, pageable);
	}

}
