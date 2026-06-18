package com.example.batch_demo.customers.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.Partitioner;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.batch_demo.customers.batch.constants.CustomersConstants.CITIES;

@Slf4j
@Component
public class CustomerCityPartitioner implements Partitioner {

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        log.info("Creating partitions");

        Map<String, ExecutionContext> partitions = new HashMap<>();

        for (int i = 0; i < CITIES.size(); i++) {

            ExecutionContext context = new ExecutionContext();
            context.putString("city", CITIES.get(i));

            partitions.put(
                    "partition-" + i,
                    context
            );
        }

        return partitions;
    }
}
