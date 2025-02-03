package com.lmlasmo.shrul.repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.shrul.model.UrlAccess;

public interface UrlAccessRepository extends JpaRepository<UrlAccess, BigInteger>{

	public void deleteByLinkId(String id);

	public Page<UrlAccess> findByLinkPrefixUserId(BigInteger user_id, Pageable pageable);

	public Page<UrlAccess> findByLinkPrefixUserIdAndAccessTimeBetween(BigInteger user_id, LocalDateTime now, LocalDateTime lastWeek, Pageable pageable);

	public void deleteByExpirationDateAfter(LocalDate now);

}
