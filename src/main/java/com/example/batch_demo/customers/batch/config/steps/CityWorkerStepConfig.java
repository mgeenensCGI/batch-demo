package com.example.batch_demo.customers.batch.config.steps;

import com.example.batch_demo.customers.batch.CustomerCityPartitioner;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CITY_WORKER_STEP_NAME;
import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.MASTER_PARTITION_STEP_NAME;

@Configuration
public class CityWorkerStepConfig {

    @Bean
    public Step masterPartitionStep(JobRepository jobRepository,
                                    CustomerCityPartitioner partitioner,
                                    Step cityWorkerStep,
                                    TaskExecutor partitionTaskExecutor) {

        return new StepBuilder(MASTER_PARTITION_STEP_NAME,jobRepository)
                .partitioner(cityWorkerStep.getName(),partitioner)
                .step(cityWorkerStep)
                .gridSize(4)
                .taskExecutor(partitionTaskExecutor)
                .build();
    }

    @Bean
    public Step cityWorkerStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               JdbcPagingItemReader<CustomerEntity> customerReaderByCity,
                               ItemProcessor<CustomerEntity, CustomerEntity> customerCityProcessor,
                               ItemWriter<CustomerEntity> updateItemWriter) {

        return new StepBuilder(CITY_WORKER_STEP_NAME, jobRepository)
                .<CustomerEntity, CustomerEntity>chunk(100)
                .reader(customerReaderByCity)
                .processor(customerCityProcessor)
                .writer(updateItemWriter)
                .transactionManager(transactionManager)
                .build();
    }
}
