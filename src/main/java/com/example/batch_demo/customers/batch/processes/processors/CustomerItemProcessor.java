package com.example.batch_demo.customers.batch.processes.processors;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.mappers.CustomerMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Transforms a CSV record into a persistence entity.
 */
@Slf4j
@Component
public class CustomerItemProcessor implements ItemProcessor<CustomerCsvRecord, CustomerEntity> {

    private final CustomerMapper customerMapper;

    public CustomerItemProcessor(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerEntity process(CustomerCsvRecord item) {


        log.info(
                "Processing customer {} thread {}",
                item.id(),
                Thread.currentThread().getName()
        );
        return customerMapper.toEntity(item);
    }
}
