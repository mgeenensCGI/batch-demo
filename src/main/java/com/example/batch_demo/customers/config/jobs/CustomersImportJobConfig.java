package com.example.batch_demo.customers.config.jobs;

import com.example.batch_demo.customers.listeners.CustomJobListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomersImportJobConfig {

    @Bean
    public Job customersImportJob(JobRepository jobRepository, Step customersImportStep) {
        return new JobBuilder("customersImportJob", jobRepository)
                .listener(new CustomJobListener())
                .start(customersImportStep)
                .build();
    }

}
