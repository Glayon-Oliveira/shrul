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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "url_access")
public class UrlAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private BigInteger id;

	@Column
	@NonNull
	private byte[] ip;

	@Column
	@NonNull
	private String browser;

	@Column
	@NonNull
	private String device;

	@Column
	private LocalDateTime accessTime = LocalDateTime.now();

	@Column
	private LocalDate expirationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_link")
	@NonNull
	private Link link;
	
	public UrlAccess(BigInteger id) {
		this.id = id;
	}	

	public UrlAccess(UrlAccessDTO dto) throws UnknownHostException {
		this.ip = InetAddress.getByName(dto.getIp()).getAddress();
		this.browser = dto.getBrowser();
		this.device = dto.getDevice();
		this.accessTime = dto.getAccessTime();
		this.link = new Link();
		this.link.setId(dto.getLinkId());
	}	

}
