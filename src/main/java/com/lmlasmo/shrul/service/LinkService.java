package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.LinkDTO;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.util.LinkCodeCreator;

import jakarta.validation.Valid;

@Service
@Transactional
public class LinkService {
	
	private LinkRepository repository;
	
	public LinkService(LinkRepository repository) {
		this.repository = repository;		
	}

	public LinkDTO save(@Valid LinkDTO linkDTO) {
		
		Link link = new Link(linkDTO);
		
		String id = "0000000000";
		
		do {					
			
			String prefixId = link.getPrefix().getId().toString();
			String destine = link.getDestination();
			String now = LocalDateTime.now().toString();			
			
			id = LinkCodeCreator.create(prefixId, destine, now);
			
		}while(repository.existsById(id));
		
		link.setId(id);		
		link = repository.save(link);
		
		return new LinkDTO(link);
	}

	public boolean delete(String id) {

		if(!repository.existsById(id)) return false;
		
		repository.deleteById(id);
		
		return !repository.existsById(id);
	}
	
	public LinkDTO findById(String id) {
		
		Optional<LinkDTO> link = repository.findById(id).map(l -> new LinkDTO(l));
		
		if(link.isPresent()) {
			return link.get();
		}
		
		return null;
	}

	public Page<LinkDTO> findByPrefix(BigInteger id, Pageable pageable) {

		return repository.findByPrefixId(id, pageable).map(l -> new LinkDTO(l));		
	}

	public String getDestination(String id) {
		
		Optional<Link> link = repository.findById(id);			
		
		return (link.isPresent()) ? link.get().getDestination() : null;
	}

	public String getDestination(String id, String prefix) {
		
		Link link = repository.findByIdAndPrefixPrefix(id, prefix);
		
		return (link != null) ? link.getDestination() : null;
	}

}
