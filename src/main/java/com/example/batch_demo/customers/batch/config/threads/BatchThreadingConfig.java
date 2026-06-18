package com.example.batch_demo.customers.batch.config.threads;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchThreadingConfig {

    @Bean
    public AsyncTaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadNamePrefix("customer-import-");
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(20);

        executor.initialize();

        return executor;
    }

    @Bean
    public TaskExecutor partitionTaskExecutor() {

        SimpleAsyncTaskExecutor taskExecutor =
                new SimpleAsyncTaskExecutor("partition-");

        taskExecutor.setConcurrencyLimit(4);

        return taskExecutor;
    }

}
