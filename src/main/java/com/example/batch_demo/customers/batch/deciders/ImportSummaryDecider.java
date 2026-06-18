package com.example.batch_demo.customers.batch.deciders;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.NO_DATA;

@Slf4j
@Component
public class ImportSummaryDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, @Nullable StepExecution stepExecution) {
        if (stepExecution == null) {
            return new FlowExecutionStatus(NO_DATA);
        }

        if (stepExecution.getWriteCount() > 0) {
            return new FlowExecutionStatus("GENERATE_SUMMARY");
        }

        log.info("Import step wrote nothing");

        return new FlowExecutionStatus(NO_DATA);
    }
}
