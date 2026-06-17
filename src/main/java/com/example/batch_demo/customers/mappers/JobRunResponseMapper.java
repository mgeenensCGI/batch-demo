package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.domain.JobRunResponse;
import org.springframework.batch.core.job.JobExecution;

public class JobRunResponseMapper {

    private JobRunResponseMapper() {
    }

    /**
     * Maps the batch execution to the API response.
     */
    public static JobRunResponse mapExecutionToResponse(JobExecution execution) {
        return new JobRunResponse(
                execution.getJobInstance().getJobName(),
                execution.getId(),
                execution.getStatus().name(),
                "Customer report job trigger accepted"
        );
    }
}
