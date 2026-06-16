package com.example.batch_demo.customers.config.process;

import com.example.batch_demo.customers.batch.CustomerItemProcessor;
import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.support.CompositeItemProcessor;
import org.springframework.batch.infrastructure.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerProcessingConfig {

    /**
     * Chains validation first, then mapping to the JPA entity.
     */
    @Bean
    public ItemProcessor<CustomerCsvRecord, CustomerEntity> customerProcessor(
            BeanValidatingItemProcessor<CustomerCsvRecord> customerValidationProcessor,
            CustomerItemProcessor customerItemProcessor) {

        CompositeItemProcessor<CustomerCsvRecord, CustomerEntity> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(
                customerValidationProcessor,
                customerItemProcessor
        ));
        return processor;
    }
}
