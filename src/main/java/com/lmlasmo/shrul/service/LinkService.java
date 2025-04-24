package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.model.LinkDTO;
import com.lmlasmo.shrul.infra.exception.DestinationNotFoundException;
import com.lmlasmo.shrul.dto.register.LinkUpdateDTO;
import com.lmlasmo.shrul.infra.exception.SystemFailedException;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.util.LinkCodeCreator;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Service
@Transactional
public class LinkService {

	private LinkRepository repository;	

	public LinkDTO save(@Valid LinkDTO linkDTO) {
		Link link = new Link(linkDTO);

		String id = "0000000000";

		do {

			String prefixId = link.getPrefix().getId().toString();

			String destine = link.getDestination();
			String now = LocalDateTime.now().toString();			
			
			id = LinkCodeCreator.create(prefixId, destine, now).toLowerCase();

		}while(repository.existsById(id));

		link.setId(id);
		return new LinkDTO(repository.save(link));
	}


	public LinkDTO update(@Valid LinkUpdateDTO update) {
		Link link = repository.findById(update.getId()).orElseGet(() -> null);

		Prefix prefix = new Prefix();
		prefix.setId(update.getPrefix());

		if(link == null) throw new EntityNotFoundException("Link not found");		
		
		link.setPrefix(prefix);
		return new LinkDTO(repository.save(link));
	}

	public void delete(String id) {
		if(!repository.existsById(id)) throw new EntityNotFoundException("Link not found");		

		repository.deleteById(id);

		if(repository.existsById(id)) throw new SystemFailedException("Link not deleted");		
	}

	public LinkDTO findById(String id) {
		LinkDTO link = repository.findById(id).map(l -> new LinkDTO(l)).orElseGet(() -> null);

		if(link == null) throw new DestinationNotFoundException("Link not found");		

		return link;
	}

	public Page<LinkDTO> findByUser(BigInteger userId, Pageable pageable) {
		return repository.findByPrefixUserId(userId, pageable).map(l -> new LinkDTO(l));
	}

	public Page<LinkDTO> findByPrefix(BigInteger id, Pageable pageable) {
		return repository.findByPrefixId(id, pageable).map(l -> new LinkDTO(l));
	}

	public String getDestination(String id) {
		Link link = repository.findById(id).orElseGet(() -> null);

		if(link == null) throw new DestinationNotFoundException("Link not found");	

		return link.getDestination();
	}

	public String getDestination(String id, String prefix) {
		Link link = repository.findByIdAndPrefixPrefix(id, prefix).orElseGet(() -> null);

		if(link == null) throw new DestinationNotFoundException("Link not found");		

		return link.getDestination();
	}

}
