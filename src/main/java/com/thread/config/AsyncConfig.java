package com.thread.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync // -> Inform Spring to run the task in Back Ground using thread pool concept
public class AsyncConfig {

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(5);
		threadPoolTaskExecutor.setQueueCapacity(100); // 100 can wait in the queue
		threadPoolTaskExecutor.setThreadNamePrefix("USER-THREAD-");
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

}
