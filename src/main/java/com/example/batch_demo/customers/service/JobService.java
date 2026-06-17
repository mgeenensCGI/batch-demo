package com.example.batch_demo.customers.service;

import com.example.batch_demo.customers.exception.CustomerReportJobException;
import com.example.batch_demo.customers.exception.ErrorEnum;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobOperator jobOperator;

    public JobService(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    /**
     * Launches the configured batch job.
     */
    public JobExecution launchJob(Job job, JobParameters jobParameters) {
        try {
            return jobOperator.start(job, jobParameters);
        } catch (InvalidJobParametersException ex) {
            throw new CustomerReportJobException(ErrorEnum.INVALID_JOB_PARAMETERS, job.getName(), "Invalid %s parameters".formatted(job.getName()), ex);
        } catch (JobExecutionAlreadyRunningException ex) {
            throw new CustomerReportJobException(ErrorEnum.JOB_ALREADY_RUNNING, job.getName(), "%s is already running".formatted(job.getName()), ex);
        } catch (JobInstanceAlreadyCompleteException | JobRestartException ex) {
            throw new CustomerReportJobException(ErrorEnum.JOB_LAUNCH_ERROR, job.getName(), "%s cannot be started".formatted(job.getName()), ex);
        }
    }
}
