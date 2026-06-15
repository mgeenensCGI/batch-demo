package com.example.batch_demo.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;

public class CustomJobListener implements JobExecutionListener {

    Logger log = LoggerFactory.getLogger(CustomJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job {} Started", jobExecution.getJobInstance().getJobName());
        log.info("Job Instance Id :: {}", jobExecution.getJobInstanceId());
        log.info("Job Parameters :: {}", jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job {} Finished", jobExecution.getJobInstance().getJobName());
        log.info("Job Status :: {}", jobExecution.getStatus());
        log.info("Job Exit Status :: {}", jobExecution.getExitStatus());
    }
}
