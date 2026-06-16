package com.example.batch_demo.customers.config.process;

import com.example.batch_demo.customers.mappers.CustomerResultSetMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.example.batch_demo.customers.utils.CustomerBatchConstants.CUSTOMERS_READER_NAME;
import static com.example.batch_demo.customers.utils.SqlQueryConstants.FETCH_CUSTOMERS_ORDER_BY_ID;

@Configuration
public class CustomerReportReaderConfig {

    @Bean
    @StepScope
    public JdbcCursorItemReader<CustomerEntity> customerReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<CustomerEntity>()
                .name(CUSTOMERS_READER_NAME)
                .dataSource(dataSource)
                .sql(FETCH_CUSTOMERS_ORDER_BY_ID)
                .rowMapper(CustomerResultSetMapper::mapRow)
                .fetchSize(100)
                .verifyCursorPosition(false)
                .build();
    }
}
