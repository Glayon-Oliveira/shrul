package com.lmlasmo.shrul.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.shrul.model.Link;

public interface LinkRepository extends JpaRepository<Link, String>{

	public Page<Link> findByPrefixUserId(BigInteger userId, Pageable pageable);

	public Page<Link> findByPrefixId(BigInteger id, Pageable pageable);

	public Optional<Link> findByIdAndPrefixPrefix(String id, String prefix);

	public boolean existsByIdAndPrefixUserId(String id, BigInteger userId);

}
