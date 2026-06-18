package com.example.batch_demo.customers.batch.config.steps;

import com.example.batch_demo.customers.batch.processes.tasklets.SummaryReportTasklet;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.GENERATE_SUMMARY_REPORT_STEP_NAME;

@Configuration
public class GenerateSummaryReportStepConfig {

    @Bean
    public Step generateSummaryReportStep(JobRepository jobRepository, SummaryReportTasklet summaryReportTasklet) {
        return new StepBuilder(GENERATE_SUMMARY_REPORT_STEP_NAME, jobRepository)
                .tasklet(summaryReportTasklet)
                .build();
    }
}
