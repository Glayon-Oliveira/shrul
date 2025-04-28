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

import com.lmlasmo.shrul.dto.PrefixDTO;
import com.lmlasmo.shrul.dto.register.RegisterPrefixDTO;
import com.lmlasmo.shrul.dto.update.PrefixUpdateDTO;
import com.lmlasmo.shrul.infra.security.AuthenticatedUser;
import com.lmlasmo.shrul.service.PrefixService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/prefix")
@ResponseStatus(code = HttpStatus.OK)
public class PrefixController {

	private PrefixService service;

	@PostMapping
	@ResponseBody
	public PrefixDTO register(@RequestBody @Valid RegisterPrefixDTO prefix){
		return service.save(prefix, AuthenticatedUser.getUserId());
	}

	@PutMapping
	@ResponseBody
	@PreAuthorize("@prefixService.existsByIdAndAuth(#update.id)")
	public PrefixDTO update(@RequestBody @Valid PrefixUpdateDTO update){
		return service.update(update);
	}

	@DeleteMapping
	@PreAuthorize("@prefixService.existsByIdAndAuth(#id)")
	public Void delete(@RequestParam @Min(1) BigInteger id){
		service.delete(id);
		return null;
	}

	@GetMapping("/empty")
	@ResponseBody
	public PrefixDTO findEmptyPrefix(){
		return service.findByEmptyPrefix(AuthenticatedUser.getUserId());
	}

	@GetMapping
	@ResponseBody
	public Page<PrefixDTO> findPrefixes(Pageable pageable){
		return service.findByUser(AuthenticatedUser.getUserId(), pageable);
	}

}