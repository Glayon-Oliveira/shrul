package com.lmlasmo.shrul.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.repository.PrefixRepository;

@Service
@Transactional
public class AccessVerifyService {

	private PrefixRepository prefixRepository;
	private LinkRepository linkRepository;

	@Autowired
	public AccessVerifyService(PrefixRepository prefixRepository, LinkRepository linkRepository) {
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

	public static String getUserEmail() {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user.getEmail();
		}catch(Exception e) {
			return null;
		}
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
