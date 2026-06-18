package com.example.batch_demo.customers.batch.config.processes.writers;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomersWriterConfig {

    @Bean
    public JpaItemWriter<CustomerEntity> itemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<CustomerEntity>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true) // Use persist instead of merge for new entities
                .build();
    }

    @Bean
    public JpaItemWriter<CustomerEntity> updateItemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<CustomerEntity>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(false)
                .build();
    }
}
