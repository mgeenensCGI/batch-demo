package com.example.batch_demo.customers.batch.config.processes.processors;

import com.example.batch_demo.customers.batch.processes.processors.CityProcessor;
import com.example.batch_demo.customers.batch.processes.processors.CustomerItemProcessor;
import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.support.CompositeItemProcessor;
import org.springframework.batch.infrastructure.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
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

    @Bean
    public ItemProcessor<CustomerEntity, CustomerEntity> customerCityProcessor(CityProcessor cityProcessor) {
        return cityProcessor;
    }
}
