package com.example.batch_demo.customers.config.processes.readers;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.mappers.CustomerCsvRecordFieldSetMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.infrastructure.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import static com.example.batch_demo.customers.constants.CustomerBatchConstants.CUSTOMERS_CSV_READER_NAME;
import static com.example.batch_demo.customers.constants.CustomersConstants.*;

/**
 * Configure the customer.csv file Reader
 */
@Configuration
public class CustomersReaderConfig {

    @Bean
    @StepScope
    public SynchronizedItemStreamReader<CustomerCsvRecord> customerCsvReader(@Value("#{jobParameters['inputFile']}") String inputFile) {
        FlatFileItemReader<CustomerCsvRecord> delegate = new FlatFileItemReaderBuilder<CustomerCsvRecord>()
                .name(CUSTOMERS_CSV_READER_NAME)
                .resource(new ClassPathResource(inputFile))
                .linesToSkip(1) // Skip the header line
                .delimited()
                .delimiter(",")
                .names(ID, FIRST_NAME, LAST_NAME, EMAIL, CITY, CREATED_AT)
                .fieldSetMapper(new CustomerCsvRecordFieldSetMapper())
                .build();

        return new SynchronizedItemStreamReaderBuilder<CustomerCsvRecord>()
                .delegate(delegate)
                .build();
    }
}
