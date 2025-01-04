package com.lmlasmo.shrul.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.shrul.model.Link;

public interface LinkRepository extends JpaRepository<Link, String>{
	
	public List<Link> findByPrefixId(BigInteger id);
	
}
