package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lmlasmo.shrul.dto.model.UrlAccessDTO;
import com.lmlasmo.shrul.model.UrlAccess;
import com.lmlasmo.shrul.repository.UrlAccessRepository;

@Service
public class UrlAccessService {
	
	private UrlAccessRepository repository;	
	
	@Autowired
	public UrlAccessService(UrlAccessRepository repository) {
		this.repository = repository;		
	}
	
	public void save(UrlAccessDTO dto) throws UnknownHostException {
		
		UrlAccess access = new UrlAccess(dto);
		
		LocalDate expires = LocalDate.now().plusMonths(1);
		
		access.setExpirationDate(expires);		
	
		repository.save(access);	
	}
	
	public void delete(BigInteger id) {				

		repository.deleteById(id);
	}
	
	public void deleteByLink(String id) {
		
		repository.deleteByLinkId(id);		
	}
	
	public void deleteByExpired() {
		
		LocalDate now = LocalDate.now();
		
		repository.deleteByExpirationDateAfter(now);
	}
	
	public Page<UrlAccessDTO> findByLastWeek(BigInteger user_id, Pageable pageable){
		
		LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime lastWeek = now.minusWeeks(1);
		
		return repository.findByLinkPrefixUserIdAndAccessTimeBetween(user_id, now, lastWeek, pageable).map(a -> new UrlAccessDTO(a));		
	}
	
	public Page<UrlAccessDTO> findByLastMonth(BigInteger user_id, Pageable pageable){
				
		LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime lastMonth = now.minusMonths(1);
		
		return repository.findByLinkPrefixUserIdAndAccessTimeBetween(user_id, now, lastMonth, pageable).map(a -> new UrlAccessDTO(a));
	}
	
	public Page<UrlAccessDTO> findAll(BigInteger user_id, Pageable pageable){
		return repository.findByLinkPrefixUserId(user_id, pageable).map(a -> new UrlAccessDTO(a));
	}

	public UrlAccessRepository getRepository() {
		return repository;
	}
	
}
