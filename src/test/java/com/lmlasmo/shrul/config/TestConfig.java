package com.lmlasmo.shrul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class TestConfig {

	@Bean
	public TaskExecutor taskExecutor() {
		return new SyncTaskExecutor();
	}

}
