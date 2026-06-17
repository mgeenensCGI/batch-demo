package com.example.batch_demo.customers.config.steps;

import com.example.batch_demo.customers.listeners.CustomerStepListener;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.batch_demo.customers.constants.CustomerBatchConstants.CUSTOMER_REPORT_EXPORT_STEP_NAME;
import static com.example.batch_demo.customers.constants.CustomerBatchConstants.CUSTOMER_REPORT_INSERT_STEP_NAME;

@Configuration
public class CustomersReportStepConfig {

    @Bean
    public Step customersReportInsertStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<CustomerEntity> reader,
            ItemProcessor<CustomerEntity, CustomerReportEntity> processor,
            JdbcBatchItemWriter<CustomerReportEntity> writer,
            CustomerStepListener stepListener) {
        return new StepBuilder(CUSTOMER_REPORT_INSERT_STEP_NAME, jobRepository)
                .<CustomerEntity, CustomerReportEntity>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(stepListener)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step customerReportExportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcPagingItemReader<CustomerReportEntity> customerReportReader,
            FlatFileItemWriter<CustomerReportEntity> customerReportWriter) {

        return new StepBuilder(CUSTOMER_REPORT_EXPORT_STEP_NAME,jobRepository)
                .<CustomerReportEntity, CustomerReportEntity>chunk(
                        100)
                .transactionManager(transactionManager)
                .reader(customerReportReader)
                .writer(customerReportWriter)
                .build();
    }

}
