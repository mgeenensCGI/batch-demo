package com.example.batch_demo.customers.config.process;

import com.example.batch_demo.customers.mappers.CustomerReportMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerReportProcessingConfig implements ItemProcessor<CustomerEntity, CustomerReportEntity> {

    @Override
    public @Nullable CustomerReportEntity process(CustomerEntity item) throws Exception {
        return CustomerReportMapper.customerToCustomerReport(item);
    }
}
