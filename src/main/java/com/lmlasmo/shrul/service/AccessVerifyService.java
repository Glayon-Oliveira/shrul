package com.lmlasmo.shrul.service;

import java.math.BigInteger;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.repository.PrefixRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Service
@Transactional
public class AccessVerifyService {

	private PrefixRepository prefixRepository;
	private LinkRepository linkRepository;	

	public static BigInteger getUserId() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getId();
	}

	public static String getUserEmail() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getEmail();		
	}

	public boolean verifyPrefixAccess(BigInteger id) {
		BigInteger userId = getUserId();
		return prefixRepository.existsByIdAndUserId(id, userId);
	}

	public boolean verifyLinkAccess(String id) {

		BigInteger userId = getUserId();

		return linkRepository.existsByIdAndPrefixUserId(id, userId);
	}

}
