package com.building.mykart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "customExecutor")
    public Executor customExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);      // keep 5 threads alive
        executor.setMaxPoolSize(10);      // can grow to 10
        executor.setQueueCapacity(100);   // waiting tasks
        executor.setThreadNamePrefix("MyKart-");

        executor.initialize();

        return executor;
    }

}
