package com.lmlasmo.shrul.service;

import java.math.BigInteger;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.model.UserDTO;
import com.lmlasmo.shrul.dto.register.DeleteAccountDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.register.UserUpdateDTO;
import com.lmlasmo.shrul.infra.erro.GenericException;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Service
@Transactional
public class UserService {

	private UserRepository repository;
	private PasswordEncoder encoder;	

	public UserDTO save(SignupDTO signup) {
		if (repository.existsByEmail(signup.getEmail())) throw new EntityExistsException("Email is not available");		

		User user = new User(signup);
		user.setPassword(encoder.encode(signup.getPassword()));

		Prefix prefix = new Prefix();
		prefix.setUser(user);
		user.getPrefixes().add(prefix);
	
		return new UserDTO(repository.save(user));
	}

	public void delete(DeleteAccountDTO delete, BigInteger id) {
		User user = repository.findById(id).orElseGet(() -> null);

		if(user == null) throw new EntityNotFoundException("User not found");

		boolean confirm = new BCryptPasswordEncoder().matches(delete.getPassword(), user.getPassword());

		if(!confirm) throw new GenericException("Invalid password");

		repository.delete(user);

		if(repository.existsById(id)) throw new GenericException("User not deleted");
	}

	public UserDTO update(UserUpdateDTO update, BigInteger id) {
		User user = repository.findById(id).orElseGet(() -> null);

		if (user == null) throw new EntityNotFoundException("User not found");

		if (update.getFirstName() == null && update.getLastName() == null) return null;

		boolean fistName = update.getFirstName() != null && !user.getFirstName().equalsIgnoreCase(update.getFirstName());
		boolean lastName = update.getLastName() != null && !user.getLastName().equalsIgnoreCase(update.getLastName());						

		if (fistName) user.setFirstName(update.getFirstName());

		if (lastName) user.setLastName(update.getLastName());

		return new UserDTO(repository.save(user));
	}

	public UserDTO updatePassword(BigInteger userId, String password) {
		User user = repository.findById(userId).orElseGet(() -> null);
		return updatePassword(user, password);
	}

	public UserDTO updatePassword(String email, String password) {
		User user = repository.findByEmail(email).orElseGet(() -> null);
		return updatePassword(user, password);
	}

	private UserDTO updatePassword(User user, String password) {
		if(user == null) throw new EntityNotFoundException("User not found");		

		if(user.getPassword().equals(password)) throw new GenericException("Password used");

		user.setPassword(encoder.encode(password));
		return new UserDTO(repository.save(user));
	}

	public void setLocked(BigInteger id, boolean locked) {
		User user = repository.findById(id).orElseGet(() -> null);

		if (user == null) throw new EntityNotFoundException("User not found");

		if(user.isLocked()) throw new GenericException("User locked");

		user.setLocked(locked);
		repository.save(user);
	}

}
