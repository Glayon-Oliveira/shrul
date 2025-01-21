package com.lmlasmo.shrul.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.shrul.model.Prefix;

public interface PrefixRepository extends JpaRepository<Prefix, BigInteger>{

	public boolean existsByPrefix(String prefix);

	public boolean existsByUserIdAndPrefixIsNull(BigInteger id);

	public Page<Prefix> findByUserId(BigInteger id, Pageable pageable);

}
