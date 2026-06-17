package com.example.batch_demo.customers.service;

import com.example.batch_demo.customers.domain.JobRunResponse;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.stereotype.Service;

import static com.example.batch_demo.customers.mappers.JobRunResponseMapper.mapExecutionToResponse;
import static com.example.batch_demo.customers.utils.JobParametersUtils.buildJobParameters;

/**
 * Owns the execution rules for the customer report batch job.
 */
@Service
public class CustomerReportExecutionService {

    private final Job customerReportJob;
    private final JobService jobService;

    public CustomerReportExecutionService(Job customerReportJob, JobService jobService) {
        this.customerReportJob = customerReportJob;
        this.jobService = jobService;
    }

    /**
     * Launches the customer report job with a fresh identifying parameter.
     */
    public JobRunResponse run() {
        JobParameters jobParameters = buildJobParameters();
        JobExecution execution = this.jobService.launchJob(this.customerReportJob, jobParameters);
        return mapExecutionToResponse(execution);
    }
}
