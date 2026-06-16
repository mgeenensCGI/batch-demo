package com.example.batch_demo.customers.config.steps;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.listeners.CustomStepListener;
import com.example.batch_demo.customers.listeners.CustomerChunkListener;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CustomersImportStepConfig {

    @Bean
    public Step customersImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<CustomerCsvRecord> reader,
            ItemProcessor<CustomerCsvRecord, CustomerEntity> customerProcessor,
            JpaItemWriter<CustomerEntity> writer) {

        return new StepBuilder("customersImportStep", jobRepository)
                .<CustomerCsvRecord, CustomerEntity>chunk(10)
                .reader(reader)
                .processor(customerProcessor)
                .writer(writer)
                .listener(new CustomStepListener())
                .listener(new CustomerChunkListener())
                .transactionManager(transactionManager)
                .build();
    }
}
