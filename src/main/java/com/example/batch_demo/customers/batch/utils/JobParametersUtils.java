package com.example.batch_demo.customers.batch.utils;

import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.OUTPUT_PATH;
import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.RUN_ID;

public class JobParametersUtils {

    private JobParametersUtils() {
    }

    /**
     * Builds a unique parameter set so each manual trigger creates a new job instance.
     *
     * @return JobParameters with a unique run.id based on the current timestamp.
     */
    public static JobParameters buildJobParameters() {
        return new JobParametersBuilder()
                .addLong(RUN_ID, System.currentTimeMillis(), true)
                .toJobParameters();
    }

    /**
     * Builds a unique parameter set so each manual trigger creates a new job instance.
     *
     * @return JobParameters with a unique run.id based on the current timestamp and an output path.
     */
    public static JobParameters buildJobParameters(String outputPath) {
        return new JobParametersBuilder()
                .addLong(RUN_ID, System.currentTimeMillis(), true)
                .addString(OUTPUT_PATH, outputPath, true)
                .toJobParameters();
    }
}
