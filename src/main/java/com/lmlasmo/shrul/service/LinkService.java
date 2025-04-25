package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HexFormat;

import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.LinkDTO;
import com.lmlasmo.shrul.dto.register.RegisterLinkDTO;
import com.lmlasmo.shrul.dto.update.LinkUpdateDTO;
import com.lmlasmo.shrul.infra.exception.DestinationNotFoundException;

import com.lmlasmo.shrul.infra.exception.SystemFailedException;
import com.lmlasmo.shrul.infra.security.AuthenticatedUser;
import com.lmlasmo.shrul.mapper.LinkMapper;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.repository.LinkRepository;

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
	private LinkMapper mapper;

	public LinkDTO save(@Valid RegisterLinkDTO linkDTO) {
		Link link = mapper.dtoToLink(linkDTO);

		String id = "0000000000";

		do {

			String prefixId = link.getPrefix().getId().toString();

			String destine = link.getDestination();
			String now = LocalDateTime.now().toString();			
			
			id = createLinkCode(prefixId, destine, now).toLowerCase();

		}while(repository.existsById(id));

		link.setId(id);
		return mapper.linkToDTO(repository.save(link));
	}


	public LinkDTO update(@Valid LinkUpdateDTO update) {
		Link link = repository.findById(update.getId()).orElseGet(() -> null);

		Prefix prefix = new Prefix();
		prefix.setId(update.getPrefix());

		if(link == null) throw new EntityNotFoundException("Link not found");		
		
		link.setPrefix(prefix);
		return mapper.linkToDTO(repository.save(link));
	}

	public void delete(String id) {
		if(!repository.existsById(id)) throw new EntityNotFoundException("Link not found");		

		repository.deleteById(id);

		if(repository.existsById(id)) throw new SystemFailedException("Link not deleted");		
	}

	public LinkDTO findById(String id) {
		LinkDTO link = repository.findById(id).map(l -> mapper.linkToDTO(l)).orElseGet(() -> null);

		if(link == null) throw new DestinationNotFoundException("Link not found");		

		return link;
	}

	public Page<LinkDTO> findByUser(BigInteger userId, Pageable pageable) {
		return repository.findByPrefixUserId(userId, pageable).map(l -> mapper.linkToDTO(l));
	}

	public Page<LinkDTO> findByPrefix(BigInteger id, Pageable pageable) {
		return repository.findByPrefixId(id, pageable).map(l -> mapper.linkToDTO(l));
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
	
	public boolean existsByIdAndUser(String linkId, BigInteger userId) {
		return repository.existsByIdAndPrefixUserId(linkId, userId);
	}
	
	public boolean existsByIdAndAuth(String linkId) {
		return repository.existsByIdAndPrefixUserId(linkId, AuthenticatedUser.getUserId());
	}
	
	public static String createLinkCode(String... values) {
		String compilation = String.join("", values);

		Blake2bDigest digest = new Blake2bDigest(40);

		byte[] compilationBytes = compilation.getBytes();
		digest.update(compilationBytes, 0, compilationBytes.length);

		byte[] hash = new byte[digest.getDigestSize()];
		digest.doFinal(hash, 0);

		return HexFormat.of().formatHex(hash);
	}

}
