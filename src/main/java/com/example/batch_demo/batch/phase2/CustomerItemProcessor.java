package com.example.batch_demo.batch.phase2;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.mappers.CustomerMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Transforms a CSV record into a persistence entity.
 */
@Component
public class CustomerItemProcessor implements ItemProcessor<CustomerCsvRecord, CustomerEntity> {

    private final CustomerMapper customerMapper;

    public CustomerItemProcessor(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerEntity process(CustomerCsvRecord item) {
        return customerMapper.toEntity(item);
    }
}
