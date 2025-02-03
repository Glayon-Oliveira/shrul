package com.lmlasmo.shrul.infra.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.lmlasmo.shrul.service.UrlAccessService;

@Configuration
@EnableScheduling
public class TaskConfig {

	private UrlAccessService service;

	@Autowired
	public TaskConfig(UrlAccessService service) {
		this.service = service;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void deleteExpiredAccess() {
		service.deleteByExpired();
	}

}
