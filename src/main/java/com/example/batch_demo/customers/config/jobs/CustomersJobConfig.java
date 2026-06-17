package com.example.batch_demo.customers.config.jobs;

import com.example.batch_demo.customers.config.jobs.deciders.ImportSummaryDecider;
import com.example.batch_demo.customers.listeners.CustomerJobListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.batch_demo.customers.constants.CustomerBatchConstants.*;

@Configuration
public class CustomersJobConfig {

    @Bean
    public Job customersImportJob(JobRepository jobRepository,
                                  Step customersImportStep,
                                  Step generateSummaryReportStep,
                                  ImportSummaryDecider importSummaryDecider,
                                  CustomerJobListener listener) {
        return new JobBuilder(CUSTOMERS_IMPORT_JOB_NAME, jobRepository)
                .listener(listener)
                .start(customersImportStep)
                .next(importSummaryDecider)
                    .on(GENERATE_SUMMARY)
                    .to(generateSummaryReportStep)
                .from(importSummaryDecider)
                    .on(NO_DATA)
                    .end()
                .end()
                .build();
    }

}
