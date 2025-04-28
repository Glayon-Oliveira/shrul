package com.lmlasmo.shrul.service;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.shrul.dto.UrlAccessDTO;
import com.lmlasmo.shrul.mapper.UrlAccessMapper;
import com.lmlasmo.shrul.model.Link;
import com.lmlasmo.shrul.model.UrlAccess;
import com.lmlasmo.shrul.repository.LinkRepository;
import com.lmlasmo.shrul.repository.UrlAccessRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ua_parser.Client;
import ua_parser.Parser;

@Getter
@AllArgsConstructor
@Service
@Transactional
public class UrlAccessService {

	private UrlAccessRepository repository;
	private LinkRepository lRepository;
	private UrlAccessMapper mapper;

	@Async
	public void save(String id, HttpServletRequest request) throws UnknownHostException {
		lRepository.findAll().forEach(l -> System.out.println(l.getId()));
		Client client = new Parser().parse(request.getHeader("User-Agent"));
		byte[] ip = InetAddress.getByName(request.getRemoteAddr()).getAddress();
		String browser = getBrowser(client);
		String device = getRelativeDevice(client);

		UrlAccess access = new UrlAccess(ip, browser, device, new Link(id));
		LocalDate expires = LocalDate.now().plusMonths(1);
		access.setExpirationDate(expires);
		repository.save(access);
	}

	public void delete(BigInteger id) {
		repository.deleteById(id);
	}

	public void deleteByLink(String id) {
		repository.deleteByLinkId(id);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void deleteByExpired() {
		LocalDate now = LocalDate.now();
		repository.deleteByExpirationDateAfter(now);
	}

	public Page<UrlAccessDTO> findByLastWeek(BigInteger user_id, Pageable pageable){
		LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime lastWeek = now.minusWeeks(1);
		return repository.findByLinkPrefixUserIdAndAccessTimeBetween(user_id, now, lastWeek, pageable).map(a -> mapper.accessToDTO(a));
	}

	public Page<UrlAccessDTO> findByLastMonth(BigInteger user_id, Pageable pageable){
		LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime lastMonth = now.minusMonths(1);
		return repository.findByLinkPrefixUserIdAndAccessTimeBetween(user_id, now, lastMonth, pageable).map(a -> mapper.accessToDTO(a));
	}

	public Page<UrlAccessDTO> findAll(BigInteger user_id, Pageable pageable){
		return repository.findByLinkPrefixUserId(user_id, pageable).map(a -> mapper.accessToDTO(a));
	}

	private String getBrowser(Client client) {
		try {
			return client.userAgent.family.toLowerCase();
		}catch(Exception e) {
			return "unknown";
		}
	}

	private String getRelativeDevice(Client client) {

		try {
			String os = client.os.family.toLowerCase();
			String browser = getBrowser(client);

			if(os.contains("mobile") || browser.contains("mobile")) {
				return "Mobile";
			}

			switch(os) {
			case "android":
			case "iphone":
			case "ipad": return "Mobile";
			case "windows":
			case "macos":
			case "linux": return "Desktop";
			default: return "Other";
			}
		}catch(Exception e) {
			return "unknown";
		}

	}

}
