package com.lmlasmo.shrul.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrlAccessDTO {

	@JsonProperty("linkId")
	@JsonAlias("link_id")
	private String linkId;

	@JsonProperty
	private String ip;

	@JsonProperty
	private String browser;

	@JsonProperty
	private String device;

	@JsonProperty("accessTime")
	@JsonAlias("access_time")
	private LocalDateTime accessTime;

	/*public UrlAccessDTO(UrlAccess access) {
		this.linkId = access.getLink().getId();
		this.browser = access.getBrowser();
		this.device = access.getDevice();
		this.accessTime = access.getAccessTime();
		try {
			this.ip = InetAddress.getByAddress(access.getIp()).getHostAddress();
		} catch (UnknownHostException e) {
			this.ip = "000.000.000";
		}
	}*/

}
