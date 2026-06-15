package com.example.batch_demo.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;

public class CustomStepListener implements StepExecutionListener {

    Logger log = LoggerFactory.getLogger(CustomStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step {} Started", stepExecution.getStepName());
        log.info("Step Instance Id :: {}", stepExecution.getId());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step {} Ended", stepExecution.getStepName());
        // Read
        log.info("Read count :: {}", stepExecution.getReadCount());
        log.info("Read skip :: {}", stepExecution.getReadSkipCount());

        // Write
        log.info("Write count :: {}", stepExecution.getWriteCount());
        log.info("Write skip :: {}", stepExecution.getWriteSkipCount());

        // Commit
        log.info("Commit count :: {}", stepExecution.getCommitCount());

        return stepExecution.getExitStatus();
    }

}
