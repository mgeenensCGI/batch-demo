package com.example.batch_demo.customers.service;

import com.example.batch_demo.customers.domain.JobRunResponse;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.stereotype.Service;

import static com.example.batch_demo.customers.batch.utils.JobParametersUtils.buildJobParameters;
import static com.example.batch_demo.customers.mappers.JobRunResponseMapper.mapExecutionToResponse;

@Service
public class CustomerService {

    private final Job partitioningJob;
    private final JobService jobService;

    public CustomerService(Job partitioningJob, JobService jobService) {
        this.partitioningJob = partitioningJob;
        this.jobService = jobService;
    }

    public JobRunResponse runCityBatch() {
        JobParameters jobParameters = buildJobParameters();
        JobExecution execution = this.jobService.launchJob(this.partitioningJob, jobParameters);

        return mapExecutionToResponse(execution);
    }
}
