package com.lmlasmo.shrul.model;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lmlasmo.shrul.dto.model.UrlAccessDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_access")
public class UrlAccess {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	@Column
	private byte[] ip;
	
	@Column
	private String browser;
	
	@Column
	private String device;
	
	@Column
	private LocalDateTime accessTime = LocalDateTime.now();
	
	@Column
	private LocalDate expirationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_link")
	private Link link;
	
	public UrlAccess() {}
	
	public UrlAccess(UrlAccessDTO dto) throws UnknownHostException {
		this.ip = InetAddress.getByName(dto.getIp()).getAddress();
		this.browser = dto.getBrowser();
		this.device = dto.getDevice();
		this.accessTime = dto.getAccessTime();
		this.link = new Link();
		this.link.setId(dto.getLinkId());
	}	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public byte[] getIp() {
		return ip;
	}

	public void setIp(byte[] ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public LocalDateTime getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(LocalDateTime accessTime) {
		this.accessTime = accessTime;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}	

}
