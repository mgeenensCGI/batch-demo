package com.example.batch_demo.customers.batch.config.processes.readers;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.mappers.CustomerCsvRecordFieldSetMapper;
import com.example.batch_demo.customers.mappers.CustomerRowMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.infrastructure.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.infrastructure.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

import java.util.Map;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMERS_CSV_READER_NAME;
import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMER_READER_BY_CITY_NAME;
import static com.example.batch_demo.customers.batch.constants.CustomersConstants.*;
import static com.example.batch_demo.customers.batch.constants.SqlQueryConstants.*;

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

    @Bean
    @StepScope
    public JdbcPagingItemReader<CustomerEntity> customerReaderByCity(
            DataSource dataSource,
            @Value("#{stepExecutionContext['city']}") String city,
            CustomerRowMapper rowMapper) throws Exception {

        PostgresPagingQueryProvider queryProvider =
                new PostgresPagingQueryProvider();

        queryProvider.setSelectClause(FETCH_ALL_CUSTOMERS_SELECT_CLAUSE);
        queryProvider.setWhereClause(FETCH_ALL_CUSTOMERS_BY_CITY_WHERE_CLAUSE.formatted(city));
        queryProvider.setFromClause(FETCH_CUSTOMER_FROM_CLAUSE);

        queryProvider.setSortKeys(
                Map.of(
                        ID,
                        Order.ASCENDING
                )
        );

        return new JdbcPagingItemReaderBuilder<CustomerEntity>()
                .name(CUSTOMER_READER_BY_CITY_NAME)
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .rowMapper(rowMapper)
                .pageSize(100)
                .build();
    }
}
