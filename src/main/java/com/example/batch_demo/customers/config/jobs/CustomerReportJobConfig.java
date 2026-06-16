package com.example.batch_demo.customers.config.jobs;

import com.example.batch_demo.customers.listeners.CustomerJobListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.batch_demo.customers.utils.CustomerBatchConstants.CUSTOMER_REPORT_JOB_NAME;

@Configuration
public class CustomerReportJobConfig {

    @Bean
    public Job customerReportJob(JobRepository jobRepository,
                                 Step customersReportInsertStep,
                                 CustomerJobListener listener) {
        return new JobBuilder(CUSTOMER_REPORT_JOB_NAME, jobRepository)
                .listener(listener)
                .start(customersReportInsertStep)
                .build();
    }
}
