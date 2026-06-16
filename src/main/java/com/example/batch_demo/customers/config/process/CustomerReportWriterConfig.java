package com.example.batch_demo.customers.config.process;

import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.example.batch_demo.customers.utils.SqlQueryConstants.INSERT_CUSTOMER_REPORT;

@Configuration
public class CustomerReportWriterConfig {

    @Bean
    public JdbcBatchItemWriter<CustomerReportEntity> customerReportItemWriter(DataSource datasource) throws Exception {
        return new JdbcBatchItemWriterBuilder<CustomerReportEntity>()
                .beanMapped()
                .sql(INSERT_CUSTOMER_REPORT)
                .dataSource(datasource)
                .build();
    }
}
