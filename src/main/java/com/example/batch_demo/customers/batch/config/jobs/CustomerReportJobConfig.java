package com.example.batch_demo.customers.batch.config.jobs;

import com.example.batch_demo.customers.batch.listeners.CustomerJobListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMER_REPORT_EXPORT_JOB_NAME;
import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMER_REPORT_INSERT_JOB_NAME;

@Configuration
public class CustomerReportJobConfig {

    @Bean
    public Job customerReportInsertJob(JobRepository jobRepository,
                                       Step customersReportInsertStep,
                                       CustomerJobListener listener) {
        return new JobBuilder(CUSTOMER_REPORT_INSERT_JOB_NAME, jobRepository)
                .listener(listener)
                .start(customersReportInsertStep)
                .build();
    }

    @Bean
    public Job customerReportExportJob(
            JobRepository jobRepository,
            Step customerReportExportStep,
            CustomerJobListener listener) {

        return new JobBuilder(
                CUSTOMER_REPORT_EXPORT_JOB_NAME,
                jobRepository)
                .listener(listener)
                .start(customerReportExportStep)
                .build();
    }
}
