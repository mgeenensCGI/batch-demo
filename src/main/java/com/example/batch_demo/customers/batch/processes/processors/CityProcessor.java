package com.example.batch_demo.customers.batch.processes.processors;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CityProcessor implements ItemProcessor<CustomerEntity, CustomerEntity> {

    @Override
    public @Nullable CustomerEntity process(CustomerEntity item) throws Exception {

        item.setCity(item.getCity() == null ? "" : item.getCity().toUpperCase());

        return item;
    }
}
