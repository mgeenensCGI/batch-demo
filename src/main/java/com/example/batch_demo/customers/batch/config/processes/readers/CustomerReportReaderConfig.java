package com.example.batch_demo.customers.batch.config.processes.readers;

import com.example.batch_demo.customers.mappers.CustomerReportRowMapper;
import com.example.batch_demo.customers.mappers.CustomerResultSetMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.infrastructure.item.database.support.PostgresPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.util.Map;

import static com.example.batch_demo.customers.batch.constants.CustomersConstants.CUSTOMER_ID;
import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMERS_READER_NAME;

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

    @Bean
    @StepScope
    public JdbcPagingItemReader<CustomerReportEntity> customerReportReader(
            DataSource dataSource,
            CustomerReportRowMapper rowMapper) throws Exception {

        PostgresPagingQueryProvider queryProvider =
                new PostgresPagingQueryProvider();

        queryProvider.setSelectClause(FETCH_CUSTOMER_REPORT_SELECT_CLAUSE);

        queryProvider.setFromClause(FETCH_CUSTOMER_REPORT_FROM_CLAUSE);

        queryProvider.setSortKeys(
                Map.of(
                        CUSTOMER_ID,
                        Order.ASCENDING
                )
        );

        return new JdbcPagingItemReaderBuilder<CustomerReportEntity>()
                .name("customerReportReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .rowMapper(rowMapper)
                .pageSize(100)
                .build();
    }
}
