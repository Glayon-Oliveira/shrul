package com.lmlasmo.shrul.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.shrul.model.User;

public interface UserRepository extends JpaRepository<User, BigInteger>{
	
	public Optional<User> findByEmail(String email);

}
