package com.example.batch_demo.customers.utils;

import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;

import static com.example.batch_demo.customers.utils.CustomerBatchConstants.RUN_ID;

public class JobParametersUtils {

    private JobParametersUtils() {
    }

    /**
     * Builds a unique parameter set so each manual trigger creates a new job instance.
     */
    public static JobParameters buildJobParameters() {
        return new JobParametersBuilder()
                .addLong(RUN_ID, System.currentTimeMillis(), true)
                .toJobParameters();
    }
}
