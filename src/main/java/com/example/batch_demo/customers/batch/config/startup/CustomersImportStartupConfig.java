package com.example.batch_demo.customers.batch.config.startup;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.Arrays;
import java.util.Comparator;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.INPUT_FILE;

@Configuration
public class CustomersImportStartupConfig {

    @Bean
    public ApplicationRunner customersWorkflowRunner(
            ResourcePatternResolver resolver,
            JobRepository jobRepository,
            JobOperator jobOperator,
            Job customersImportJob) {

        return args -> {
            Resource[] files = resolver.getResources("classpath*:customers/import/*.csv");

            Arrays.stream(files)
                    .sorted(Comparator.comparing(Resource::getFilename, Comparator.nullsLast(String::compareToIgnoreCase)))
                    .forEach(file -> launchIfNeeded(file, jobRepository, jobOperator, customersImportJob));
        };
    }

    private void launchIfNeeded(Resource file,
                                JobRepository jobRepository,
                                JobOperator jobOperator,
                                Job customersImportJob) {
        String inputFile = "customers/import/" + file.getFilename();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString(INPUT_FILE, inputFile)
                .toJobParameters();

        JobExecution lastExecution =
                jobRepository.getLastJobExecution(customersImportJob.getName(), jobParameters);

        if (lastExecution != null && BatchStatus.COMPLETED.equals(lastExecution.getStatus())) {
            return;
        }

        try {
            jobOperator.start(customersImportJob, jobParameters);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to start job %s for file %s".formatted(customersImportJob.getName(), inputFile),
                    e
            );
        }
    }
}