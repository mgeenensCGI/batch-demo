package com.example.batch_demo.batch.phase2;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.listeners.CustomJobListener;
import com.example.batch_demo.listeners.CustomStepListener;
import com.example.batch_demo.listeners.CustomerChunkListener;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CustomersImportJobConfig {

    // Jobs
    @Bean
    public Job customersImportJob(JobRepository jobRepository, Step customersImportStep) {
        return new JobBuilder("customersImportJob", jobRepository)
                .listener(new CustomJobListener())
                .start(customersImportStep)
                .build();
    }

    // Steps
    @Bean
    public Step customersImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<CustomerCsvRecord> reader,
            CustomerItemProcessor processor,
            JpaItemWriter<CustomerEntity> writer) {

        return new StepBuilder("customersImportStep", jobRepository)
                .<CustomerCsvRecord, CustomerEntity>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new CustomStepListener())
                .listener(new CustomerChunkListener())
                .transactionManager(transactionManager)
                .build();
    }
}
