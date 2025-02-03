package com.lmlasmo.shrul.dto.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.model.UrlAccess;

import jakarta.servlet.http.HttpServletRequest;
import ua_parser.Client;
import ua_parser.Parser;

public class UrlAccessDTO {

	@JsonProperty("link_id")
	private String linkId;

	@JsonProperty
	private String ip;

	@JsonProperty
	private String browser;

	@JsonProperty
	private String device;

	@JsonProperty("access_time")
	private LocalDateTime accessTime;

	public UrlAccessDTO() {}

	public UrlAccessDTO(UrlAccess access) {
		this.linkId = access.getLink().getId();
		this.browser = access.getBrowser();
		this.device = access.getDevice();
		this.accessTime = access.getAccessTime();
		setIp(access.getIp());
	}

	public UrlAccessDTO(String linkId, HttpServletRequest request) {

		this.linkId = linkId;

		Client client = new Parser().parse(request.getHeader("User-Agent"));

		this.ip = request.getRemoteAddr();
		this.browser = client.userAgent.family;
		this.device = relativeDevice(client);
		this.accessTime = LocalDateTime.now();
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String link) {
		this.linkId = link;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setIp(byte[] address) {
		try {
			this.ip = InetAddress.getByAddress(address).getHostAddress();
		} catch (UnknownHostException e) {
			this.ip = "000.000.000";
		}
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

	private String relativeDevice(Client client) {

		String os = client.os.family.toLowerCase();
		String browser = client.userAgent.family.toLowerCase();

		if(os.contains("mobile") || browser.contains("mobile")) {
			return "Mobile";
		}

		switch(os) {

			case "android":
			case "iphone":
			case "ipad":
				return "Mobile";

			case "windows":
			case "macos":
			case "linux":
				return "Desktop";

			default: return "Other";
		}

	}

}
