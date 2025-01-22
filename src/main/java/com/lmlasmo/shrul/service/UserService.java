package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.model.UserDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.register.UserUpdateDTO;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class UserService {

	private UserRepository repository;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public UserDTO save(SignupDTO signup) {

		if (!repository.existsByEmail(signup.getEmail())) {

			User user = new User(signup);
			user.setPassword(encoder.encode(signup.getPassword()));

			return new UserDTO(repository.save(user));
		}

		return null;
	}

	public UserDTO update(UserUpdateDTO update) {

		Optional<User> userOp = repository.findById(update.getId());

		if (userOp.isPresent()) {

			User user = userOp.get();

			if (update.getFirstName() == null && update.getLastName() == null) {
				return null;
			}

			boolean fistName = update.getFirstName() != null && !user.getFirstName().equalsIgnoreCase(update.getFirstName());
			boolean lastName = update.getLastName() != null && !user.getLastName().equalsIgnoreCase(update.getLastName());						

			if (fistName) {
				user.setFirstName(update.getFirstName());
			}

			if (lastName) {
				user.setLastName(update.getLastName());
			}

			return new UserDTO(repository.save(user));
		}

		throw new EntityNotFoundException("User not exist");
	}

	public void setLocked(BigInteger id, boolean locked) {

		Optional<User> user = repository.findById(id);

		if (user.isPresent()) {

			user.get().setLocked(locked);

			repository.save(user.get());

			return;
		}

		throw new EntityNotFoundException("User not found");
	}

	public UserRepository getRepository() {
		return repository;
	}

}
