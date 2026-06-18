package com.example.batch_demo.customers.batch.config.steps;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.batch.listeners.CustomerChunkListener;
import com.example.batch_demo.customers.batch.listeners.CustomerSkipListener;
import com.example.batch_demo.customers.batch.listeners.CustomerStepListener;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.infrastructure.item.validator.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMERS_IMPORT_STEP_NAME;

@Configuration
public class CustomersStepConfig {

    @Bean
    public Step customersImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            SynchronizedItemStreamReader<CustomerCsvRecord> reader,
            ItemProcessor<CustomerCsvRecord, CustomerEntity> customerProcessor,
            JpaItemWriter<CustomerEntity> writer,
            CustomerStepListener stepListener,
            CustomerChunkListener chunkListener,
            CustomerSkipListener skipListener,
            AsyncTaskExecutor batchTaskExecutor) {

        return new StepBuilder(CUSTOMERS_IMPORT_STEP_NAME, jobRepository)
                .<CustomerCsvRecord, CustomerEntity>chunk(10)
                .reader(reader)
                .processor(customerProcessor)
                .writer(writer)
                .faultTolerant()
                .skip(ValidationException.class)
                .skipLimit(100)
                .listener(stepListener)
                .listener(chunkListener)
                .listener(skipListener)
                .transactionManager(transactionManager)
                .taskExecutor(batchTaskExecutor)
                .build();
    }
}
