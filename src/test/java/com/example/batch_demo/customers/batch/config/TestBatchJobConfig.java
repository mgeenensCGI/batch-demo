package com.example.batch_demo.customers.batch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBatchJobConfig {

    @Bean
    Job customersImportTestJob(
            JobRepository jobRepository,
            Step customersImportStep) {

        return new JobBuilder("customersTestJob", jobRepository)
                .start(customersImportStep)
                .build();
    }
}
