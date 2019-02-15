package tnc.at.brpl.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
@EnableAsync
@SuppressWarnings("unused")
public class AsyncConfig {


    /**
     * Create Thread Pool Size
     *
     * @return
     */
    @Bean
    public TaskExecutor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000000);
        executor.setMaxPoolSize(1000000);
        executor.setQueueCapacity(5000000);
        executor.setThreadNamePrefix("UploadToken-");
        executor.initialize();
        return executor;
    }


}
