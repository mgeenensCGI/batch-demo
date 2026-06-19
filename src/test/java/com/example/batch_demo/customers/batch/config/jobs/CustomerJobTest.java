package com.example.batch_demo.customers.batch.config.jobs;

import com.example.batch_demo.customers.batch.config.TestBatchConfig;
import com.example.batch_demo.customers.batch.deciders.ImportSummaryDecider;
import com.example.batch_demo.customers.batch.listeners.CustomerJobListener;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.JobInterruptedException;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.test.JobOperatorTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBatchTest
@SpringBootTest(classes = {
        CustomersJobConfig.class,
        TestBatchConfig.class
})
class CustomerJobTest {

    @Autowired
    private JobOperatorTestUtils jobOperatorTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private Job customersImportJob;

    @MockitoBean(name = CUSTOMERS_IMPORT_STEP_NAME)
    private Step customersImportStep;

    @MockitoBean(name = GENERATE_SUMMARY_REPORT_STEP_NAME)
    private Step generateSummaryReportStep;

    @MockitoBean
    private ImportSummaryDecider importSummaryDecider;

    @MockitoBean
    private CustomerJobListener customerJobListener;

    @PostConstruct
    void init() {
        jobOperatorTestUtils.setJob(customersImportJob);
    }

    @BeforeEach
    void setup() throws JobInterruptedException {
        jobRepositoryTestUtils.removeJobExecutions();

        configureStepMock(
                customersImportStep,
                CUSTOMERS_IMPORT_STEP_NAME);

        configureStepMock(
                generateSummaryReportStep,
                GENERATE_SUMMARY_REPORT_STEP_NAME);
    }

    private void configureStepMock(
            Step step,
            String stepName) throws JobInterruptedException {

        when(step.getName())
                .thenReturn(stepName);

        when(step.getStartLimit())
                .thenReturn(Integer.MAX_VALUE);

        when(step.isAllowStartIfComplete())
                .thenReturn(true);

        doAnswer(invocation -> {

            StepExecution stepExecution =
                    invocation.getArgument(0);

            stepExecution.setStatus(BatchStatus.COMPLETED);
            stepExecution.setExitStatus(ExitStatus.COMPLETED);

            return null;

        }).when(step).execute(any());
    }

    /**
     * Launches the configured job and returns its execution.
     */
    private JobExecution launchJob() throws Exception {
        return jobOperatorTestUtils.startJob();
    }

    @Nested
    class NoDataBranch {

        @Test
        void shouldCompleteJobWhenNoData() throws Exception {

            when(importSummaryDecider.decide(
                    any(),
                    any()))
                    .thenReturn(new FlowExecutionStatus(NO_DATA));

            JobExecution execution = launchJob();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);

            verify(importSummaryDecider)
                    .decide(any(), any());

            verifyNoInteractions(generateSummaryReportStep);
        }
    }

    @Nested
    class GenerateSummaryBranch {

        @Test
        void shouldGenerateSummaryWhenDeciderReturnsGenerateSummary()
                throws Exception {

            when(importSummaryDecider.decide(
                    any(),
                    any()))
                    .thenReturn(
                            new FlowExecutionStatus(
                                    GENERATE_SUMMARY));

            JobExecution execution = launchJob();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);

            verify(importSummaryDecider)
                    .decide(any(), any());
        }
    }

    @Nested
    class ListenerBehaviour {

        @Test
        void shouldInvokeJobListener() throws Exception {

            when(importSummaryDecider.decide(
                    any(),
                    any()))
                    .thenReturn(new FlowExecutionStatus(NO_DATA));

            launchJob();

            verify(customerJobListener, times(2))
                    .beforeJob(any());

            verify(customerJobListener, times(2))
                    .afterJob(any());
        }
    }

    @Nested
    class FailureBehaviour {

        @Test
        void shouldFailWhenCustomersImportStepFails()
                throws Exception {

            doThrow(new RuntimeException("step failure"))
                    .when(customersImportStep)
                    .execute(any());

            JobExecution execution = launchJob();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.FAILED);
        }

        @Test
        void shouldFailWhenSummaryStepFails()
                throws Exception {

            when(importSummaryDecider.decide(
                    any(),
                    any()))
                    .thenReturn(
                            new FlowExecutionStatus(
                                    GENERATE_SUMMARY));

            doThrow(new RuntimeException("summary failure"))
                    .when(generateSummaryReportStep)
                    .execute(any());

            JobExecution execution = launchJob();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.FAILED);
        }
    }
}
