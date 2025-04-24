package com.lmlasmo.shrul.service;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.model.PrefixDTO;
import com.lmlasmo.shrul.dto.register.PrefixUpdateDTO;
import com.lmlasmo.shrul.infra.exception.SystemFailedException;
import com.lmlasmo.shrul.infra.exception.UnchangedFieldException;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.repository.PrefixRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Service
@Transactional
public class PrefixService {

	private PrefixRepository repository;	

	public PrefixDTO save(PrefixDTO prefixDTO) {
		if(repository.existsByPrefix(prefixDTO.getPrefix())) throw new EntityExistsException("Prefix used");	

		Prefix prefix = new Prefix(prefixDTO);		
		return new PrefixDTO(repository.save(prefix));
	}

	public PrefixDTO update(PrefixUpdateDTO update) {
		Prefix prefix = repository.findById(update.getId()).orElseGet(() -> null);

		if(prefix == null) throw new EntityNotFoundException("Prefix not found");		

		if(prefix.getPrefix() == null) throw new UnchangedFieldException("Empty prefix cannot be updated");		

		if(repository.existsByPrefix(update.getPrefix())) throw new EntityExistsException("Prefix exists");		

		prefix.setPrefix(update.getPrefix());		
		return new PrefixDTO(repository.save(prefix));
	}

	public void delete(BigInteger id) {
		if(!repository.existsById(id)) throw new EntityNotFoundException("Prefix not found");

		repository.deleteByIdAndPrefixIsNotNull(id);

		if(repository.existsById(id)) throw new SystemFailedException("Prefix not deleted");
	}

	public Page<PrefixDTO> findByUser(BigInteger id, Pageable pageable) {
		return repository.findByUserId(id, pageable).map(p -> new PrefixDTO(p));
	}

	public PrefixDTO findByEmptyPrefix(BigInteger userId) {
		PrefixDTO prefix = repository.findByUserIdAndPrefixIsNull(userId).map(p -> new PrefixDTO(p)).orElseGet(() -> null);
		
		if(prefix == null) throw new EntityNotFoundException("Empty prefix not found");
		
		return prefix;
	}	

}
