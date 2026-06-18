package com.example.batch_demo.customers.batch.validation;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import org.springframework.batch.infrastructure.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerValidationConfig {

    @Bean
    public BeanValidatingItemProcessor<CustomerCsvRecord> customerValidationProcessor() {
        BeanValidatingItemProcessor<CustomerCsvRecord> processor =
                new BeanValidatingItemProcessor<>();
        processor.setFilter(false);
        return processor;
    }
}
