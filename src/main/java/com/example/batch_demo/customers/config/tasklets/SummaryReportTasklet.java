package com.example.batch_demo.customers.config.tasklets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import static com.example.batch_demo.customers.constants.CustomerBatchConstants.CUSTOMERS_IMPORT_STEP_NAME;

@Slf4j
@Component
public class SummaryReportTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        JobExecution jobExecution = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution();

        StepExecution importStepExecution = jobExecution.getStepExecutions().stream()
                .filter(stepExecution -> CUSTOMERS_IMPORT_STEP_NAME.equals(stepExecution.getStepName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("%s not found".formatted(CUSTOMERS_IMPORT_STEP_NAME)));

        log.info(
                """
                        
                        =========================
                        IMPORT SUMMARY
                        =========================
                        Step [{}] completed.
                        Read Count      : {}
                        Write Count     : {}
                        Read Skip Count : {}
                        Process Skip    : {}
                        Write Skip      : {}
                        Commit Count    : {}
                        Rollback Count  : {}
                        =========================
                        """,
                importStepExecution.getStepName(),
                importStepExecution.getReadCount(),
                importStepExecution.getWriteCount(),
                importStepExecution.getReadSkipCount(),
                importStepExecution.getProcessSkipCount(),
                importStepExecution.getWriteSkipCount(),
                importStepExecution.getCommitCount(),
                importStepExecution.getRollbackCount()
        );
        return RepeatStatus.FINISHED;
    }
}
