package com.example.batch_demo.customers.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Logs lifecycle information for a batch job.
 */
@Slf4j
@Component
public class CustomerJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(
                "Job [{}] Started at {} | Instance id :: {} | Parameters :: {}",
                jobExecution.getJobInstance().getJobName(),
                LocalDateTime.now(),
                jobExecution.getJobInstanceId(),
                jobExecution.getJobParameters()
        );
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Duration duration = Duration.between(
                jobExecution.getStartTime(),
                jobExecution.getEndTime()
        );

        log.info(
                "Job [{}] Finished | Status [{}] in {}ms",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus(),
                duration.toMillis()
        );

    }
}
