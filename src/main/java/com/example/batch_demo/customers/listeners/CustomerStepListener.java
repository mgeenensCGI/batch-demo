package com.example.batch_demo.customers.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info(
                "Step [{}] started",
                stepExecution.getStepName()
        );
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        log.info(
                """
                        Step [{}] completed.
                        Read Count      : {}
                        Write Count     : {}
                        Read Skip Count : {}
                        Process Skip    : {}
                        Write Skip      : {}
                        Commit Count    : {}
                        Rollback Count  : {}
                        """,
                stepExecution.getStepName(),
                stepExecution.getReadCount(),
                stepExecution.getWriteCount(),
                stepExecution.getReadSkipCount(),
                stepExecution.getProcessSkipCount(),
                stepExecution.getWriteSkipCount(),
                stepExecution.getCommitCount(),
                stepExecution.getRollbackCount()
        );

        return stepExecution.getExitStatus();
    }

}
