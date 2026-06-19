package com.example.batch_demo.customers.batch;

import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.step.StepExecution;

public class BatchTestUtils {

    private BatchTestUtils() {
        // Private constructor to prevent instantiation
    }

    public static StepExecution firstStep(JobExecution execution) {
        return execution.getStepExecutions()
                .iterator()
                .next();
    }
}
