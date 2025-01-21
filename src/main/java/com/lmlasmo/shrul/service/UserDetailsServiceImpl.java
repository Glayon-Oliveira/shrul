package com.lmlasmo.shrul.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository repository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadUserByEmail(username);
	}

	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{

		Optional<User> user = repository.findByEmail(email);

		if(user.isPresent()) {
			return user.get();
		}

		throw new UsernameNotFoundException("User not found");
	}

}
