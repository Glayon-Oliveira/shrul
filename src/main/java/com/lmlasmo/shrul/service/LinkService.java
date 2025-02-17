package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.model.LinkDTO;
import com.lmlasmo.shrul.dto.register.LinkUpdateDTO;
import com.lmlasmo.shrul.infra.erro.DestineNotFoundException;
import com.lmlasmo.shrul.infra.erro.GenericException;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.util.LinkCodeCreator;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Transactional
public class LinkService {

	private LinkRepository repository;

	@Autowired
	public LinkService(LinkRepository repository) {
		this.repository = repository;
	}

	public LinkDTO save(@Valid LinkDTO linkDTO) {

		Link link = new Link(linkDTO);

		String id = "0000000000";

		do {

			String prefixId = link.getPrefix().getId().toString();
			String destine = link.getDestine();
			String now = LocalDateTime.now().toString();

			id = LinkCodeCreator.create(prefixId, destine, now).toLowerCase();

		}while(repository.existsById(id));

		link.setId(id);
		link = repository.save(link);

		return new LinkDTO(link);
	}


	public LinkDTO update(@Valid LinkUpdateDTO update) {

		Optional<Link> linkOp = repository.findById(update.getId());

		Prefix prefix = new Prefix();
		prefix.setId(update.getPrefix());

		if(linkOp.isEmpty()) {
			throw new EntityNotFoundException("Link not found");
		}

		Link link = linkOp.get();
		link.setPrefix(prefix);

		link = repository.save(link);

		return new LinkDTO(link);
	}

	public void delete(String id) {

		if(!repository.existsById(id)) {
			throw new EntityNotFoundException("Link not found");
		}

		repository.deleteById(id);

		if(repository.existsById(id)) {
			throw new GenericException("Link not deleted");
		}
	}

	public LinkDTO findById(String id) {

		Optional<LinkDTO> link = repository.findById(id).map(l -> new LinkDTO(l));

		if(link.isEmpty()) {
			throw new DestineNotFoundException("Link not found");
		}

		return link.get();
	}

	public Page<LinkDTO> findByUser(BigInteger userId, Pageable pageable) {
		return repository.findByPrefixUserId(userId, pageable).map(l -> new LinkDTO(l));
	}

	public Page<LinkDTO> findByPrefix(BigInteger id, Pageable pageable) {
		return repository.findByPrefixId(id, pageable).map(l -> new LinkDTO(l));
	}

	public String getDestine(String id) {

		Optional<Link> link = repository.findById(id);

		if((link.isEmpty())) {
			throw new DestineNotFoundException("Link not found");
		}

		return link.get().getDestine();
	}

	public String getDestine(String id, String prefix) {

		Optional<Link> link = repository.findByIdAndPrefixPrefix(id, prefix);

		if(link.isEmpty()) {
			throw new DestineNotFoundException("Link not found");
		}

		return link.get().getDestine();
	}

	public LinkRepository getRepository() {
		return repository;
	}

}
