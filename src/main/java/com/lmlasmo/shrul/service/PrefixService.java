package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.model.PrefixDTO;
import com.lmlasmo.shrul.dto.register.PrefixUpdateDTO;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.repository.PrefixRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PrefixService {

	private PrefixRepository repository;

	@Autowired
	public PrefixService(PrefixRepository repository) {
		this.repository = repository;
	}

	public PrefixDTO save(PrefixDTO prefixDTO, BigInteger userId) {

		if(repository.existsByPrefix(prefixDTO.getPrefix())) {
			throw new EntityExistsException("Prefix exists");
		}

		Prefix prefix = new Prefix(prefixDTO, userId);
		prefix = repository.save(prefix);

		return new PrefixDTO(prefix);
	}

	public PrefixDTO update(PrefixUpdateDTO update) {

		Optional<Prefix> prefixOp = repository.findById(update.getId());

		if(!prefixOp.isPresent()) {			
			throw new EntityNotFoundException("Prefix not found");
		}

		Prefix prefix = prefixOp.get();
		
		if(prefix.getPrefix() == null) {
			throw new NullPointerException("Empty prefix cannot be updated"); 
		}

		if(!repository.existsByPrefix(update.getPrefix())) {

			prefix.setPrefix(update.getPrefix());
			prefix = repository.save(prefix);

			return new PrefixDTO(prefix);
		}

		throw new EntityExistsException("Prefix exists");
	}

	public boolean delete(BigInteger id) {

		if(!repository.existsById(id)) {
			return false;
		}

		repository.deleteByIdAndPrefixIsNotNull(id);

		return !repository.existsById(id);
	}

	public Page<PrefixDTO> findByUser(BigInteger id, Pageable pageable) {

		return repository.findByUserId(id, pageable).map(p -> new PrefixDTO(p));
	}

	public PrefixDTO findByEmptyPrefix(BigInteger userId) {
		
		return repository.findByUserIdAndPrefixIsNull(userId);
	}

}
