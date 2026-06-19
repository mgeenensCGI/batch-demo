package com.example.batch_demo.customers.batch.config.jobs;

import com.example.batch_demo.customers.batch.listeners.CustomerJobListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.PARTITIONING_JOB_NAME;

@Configuration
public class PartitionJobConfig {

    @Bean
    public Job partitioningJob(CustomerJobListener listener,
                               JobRepository jobRepository,
                               Step masterPartitionStep) {

        return new JobBuilder(PARTITIONING_JOB_NAME, jobRepository)
                .listener(listener)
                .start(masterPartitionStep)
                .build();
    }

}
