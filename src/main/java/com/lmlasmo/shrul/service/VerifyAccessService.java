package com.lmlasmo.shrul.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.repository.PrefixRepository;

@Service
public class VerifyAccessService {
		
	private PrefixRepository prefixRepository;	
	private LinkRepository linkRepository;
	
	@Autowired
	public VerifyAccessService(PrefixRepository prefixRepository, LinkRepository linkRepository) {		
		this.prefixRepository = prefixRepository;		
		this.linkRepository = linkRepository;
	}
	
	public static BigInteger getUserId() {
		
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user.getId();
		}catch(Exception e) {
			return BigInteger.valueOf(0);
		}				
				
	}	
	
	public boolean verifyAccessPrefix(BigInteger id) {
		
		BigInteger userId = getUserId();
		
		return prefixRepository.existsByIdAndUserId(id, userId);
	}	 
	
	public boolean verifyAccessLink(String id) {
		
		BigInteger userId = getUserId();
				
		return linkRepository.existsByIdAndPrefixUserId(id, userId);
	}

}
