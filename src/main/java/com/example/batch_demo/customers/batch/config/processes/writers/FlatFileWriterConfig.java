package com.example.batch_demo.customers.batch.config.processes.writers;

import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMER_REPORT_FILE_WRITER_NAME;
import static com.example.batch_demo.customers.batch.constants.CustomersConstants.*;

@Configuration
public class FlatFileWriterConfig {

    @Bean
    @StepScope
    public FlatFileItemWriter<CustomerReportEntity> customerReportWriter(
            @Value("#{jobParameters['outputPath']}") String outputPath) {

        return new FlatFileItemWriterBuilder<CustomerReportEntity>()
                .name(CUSTOMER_REPORT_FILE_WRITER_NAME)
                .resource(new FileSystemResource(outputPath))
                .headerCallback(writer -> writer.write(CUSTOMER_REPORT_FILE_HEADER))
                .delimited()
                .delimiter(",")
                .names(CUSTOMERID, FULLNAME, EMAIL, CITY)
                .build();
    }
}
