package com.example.batch_demo.batch.phase1;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Batch configuration for the first runnable job.
 */
@Configuration
public class HelloWorldJobConfig {

    @Bean
    public Job helloWorldJob(JobRepository jobRepository, Step helloWorldStep) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloWorldStep)
                .build();
    }

    @Bean
    public Step helloWorldStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            HelloWorldTasklet helloWorldTasklet
    ) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(helloWorldTasklet, transactionManager)
                .build();
    }
}
