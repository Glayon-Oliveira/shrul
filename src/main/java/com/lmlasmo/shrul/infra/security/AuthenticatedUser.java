package com.lmlasmo.shrul.infra.security;

import java.math.BigInteger;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lmlasmo.shrul.model.User;

public interface AuthenticatedUser {

	public static BigInteger getUserId() {
		User user = getUser();
		if(user != null) {
			return user.getId();
		}
		return null;
	}

	public static User getUser() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}

	public static String getUserEmail() {
		User user = getUser();
		if(user != null) {
			return user.getEmail();
		}
		return null;
	}

}
