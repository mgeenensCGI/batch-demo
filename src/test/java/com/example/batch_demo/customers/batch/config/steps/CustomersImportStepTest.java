package com.example.batch_demo.customers.batch.config.steps;

import com.example.batch_demo.customers.batch.config.TestBatchConfig;
import com.example.batch_demo.customers.batch.config.TestBatchJobConfig;
import com.example.batch_demo.customers.batch.listeners.CustomerChunkListener;
import com.example.batch_demo.customers.batch.listeners.CustomerSkipListener;
import com.example.batch_demo.customers.batch.listeners.CustomerStepListener;
import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.infrastructure.item.validator.ValidationException;
import org.springframework.batch.test.JobOperatorTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.stream.IntStream;

import static com.example.batch_demo.customers.batch.BatchTestUtils.firstStep;
import static com.example.batch_demo.customers.batch.constants.CustomerBatchConstants.CUSTOMERS_IMPORT_STEP_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBatchTest
@SpringBootTest(classes = {
        CustomersStepConfig.class,
        TestBatchConfig.class,
        TestBatchJobConfig.class
})
class CustomersImportStepTest {

    @Autowired
    private JobOperatorTestUtils jobOperatorTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private Job customersImportTestJob;

    @MockitoBean
    private SynchronizedItemStreamReader<CustomerCsvRecord> reader;

    @MockitoBean
    private ItemProcessor<CustomerCsvRecord, CustomerEntity> customerProcessor;

    @MockitoBean
    private JpaItemWriter<CustomerEntity> itemWriter;

    @MockitoBean
    private CustomerStepListener stepListener;

    @MockitoBean
    private CustomerChunkListener chunkListener;

    @MockitoBean
    private CustomerSkipListener skipListener;

    @PostConstruct
    void init() {
        jobOperatorTestUtils.setJob(customersImportTestJob);
    }

    @BeforeEach
    void cleanMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobExecution launchStep() {
        return jobOperatorTestUtils.startStep(CUSTOMERS_IMPORT_STEP_NAME);
    }

    @Nested
    class HappyPath {

        @Test
        void shouldCompleteStepWhenInputIsEmpty() throws Exception {

            when(reader.read()).thenReturn(null);

            JobExecution execution = launchStep();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);

            StepExecution stepExecution = firstStep(execution);

            assertThat(stepExecution.getReadCount()).isZero();
            assertThat(stepExecution.getWriteCount()).isZero();

            verifyNoInteractions(customerProcessor);
            verifyNoInteractions(itemWriter);
        }

        @Test
        void shouldReadProcessAndWriteRecords() throws Exception {

            CustomerCsvRecord r1 = mock(CustomerCsvRecord.class);
            CustomerCsvRecord r2 = mock(CustomerCsvRecord.class);

            CustomerEntity e1 = new CustomerEntity();
            CustomerEntity e2 = new CustomerEntity();

            when(reader.read())
                    .thenReturn(r1)
                    .thenReturn(r2)
                    .thenReturn(null);

            when(customerProcessor.process(r1)).thenReturn(e1);
            when(customerProcessor.process(r2)).thenReturn(e2);

            JobExecution execution = launchStep();

            StepExecution stepExecution = firstStep(execution);

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);

            assertThat(stepExecution.getReadCount()).isEqualTo(2);
            assertThat(stepExecution.getWriteCount()).isEqualTo(2);

            verify(itemWriter).write(any());
        }

        @Test
        void shouldProcessSeveralChunks() throws Exception {

            List<CustomerCsvRecord> csvRecords =
                    IntStream.range(0, 25)
                            .mapToObj(i -> mock(CustomerCsvRecord.class))
                            .toList();

            OngoingStubbing<CustomerCsvRecord> stub =
                    when(reader.read());

            for (CustomerCsvRecord csvRecord : csvRecords) {
                stub = stub.thenReturn(csvRecord);
                when(customerProcessor.process(csvRecord))
                        .thenReturn(new CustomerEntity());
            }

            stub.thenReturn(null);

            JobExecution execution = launchStep();

            StepExecution stepExecution = firstStep(execution);

            assertThat(stepExecution.getReadCount()).isEqualTo(25);
            assertThat(stepExecution.getWriteCount()).isEqualTo(25);

            verify(itemWriter, times(3))
                    .write(any());
        }
    }

    @Nested
    class SkipBehaviour {

        @Test
        void shouldSkipValidationExceptionThrownByProcessor() throws Exception {

            CustomerCsvRecord csvRecord = mock(CustomerCsvRecord.class);

            when(reader.read())
                    .thenReturn(csvRecord)
                    .thenReturn(null);

            when(customerProcessor.process(csvRecord))
                    .thenThrow(new ValidationException("invalid"));

            JobExecution execution = launchStep();

            StepExecution stepExecution = firstStep(execution);

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);

            assertThat(stepExecution.getProcessSkipCount())
                    .isEqualTo(1);
        }

        @Test
        void shouldCompleteWhenSkipLimitIsReached() throws Exception {

            OngoingStubbing<CustomerCsvRecord> stub =
                    when(reader.read());

            for (int i = 0; i < 100; i++) {

                CustomerCsvRecord csvRecord =
                        mock(CustomerCsvRecord.class);

                stub = stub.thenReturn(csvRecord);

                when(customerProcessor.process(csvRecord))
                        .thenThrow(new ValidationException("invalid"));
            }

            stub.thenReturn(null);

            JobExecution execution = launchStep();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.COMPLETED);
        }

        @Test
        void shouldFailWhenSkipLimitIsExceeded() throws Exception {

            OngoingStubbing<CustomerCsvRecord> stub =
                    when(reader.read());

            for (int i = 0; i < 101; i++) {

                CustomerCsvRecord csvRecord =
                        mock(CustomerCsvRecord.class);

                stub = stub.thenReturn(csvRecord);

                when(customerProcessor.process(csvRecord))
                        .thenThrow(new ValidationException("invalid"));
            }

            stub.thenReturn(null);

            JobExecution execution = launchStep();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.FAILED);
        }
    }

    @Nested
    class FailureBehaviour {

        @Test
        void shouldFailWhenProcessorThrowsUnexpectedException() throws Exception {

            CustomerCsvRecord csvRecord = mock(CustomerCsvRecord.class);

            when(reader.read())
                    .thenReturn(csvRecord);

            when(customerProcessor.process(csvRecord))
                    .thenThrow(new IllegalStateException("boom"));

            JobExecution execution = launchStep();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.FAILED);
        }

        @Test
        void shouldFailWhenWriterThrowsUnexpectedException() throws Exception {

            CustomerCsvRecord csvRecord = mock(CustomerCsvRecord.class);

            when(reader.read())
                    .thenReturn(csvRecord)
                    .thenReturn(null);

            when(customerProcessor.process(csvRecord))
                    .thenReturn(new CustomerEntity());

            doThrow(new RuntimeException("writer error"))
                    .when(itemWriter)
                    .write(any());

            JobExecution execution = launchStep();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.FAILED);
        }

        @Test
        void shouldFailWhenReaderThrowsUnexpectedException() throws Exception {

            when(reader.read())
                    .thenThrow(new IllegalStateException("reader error"));

            JobExecution execution = launchStep();

            assertThat(execution.getStatus())
                    .isEqualTo(BatchStatus.FAILED);

            StepExecution stepExecution = firstStep(execution);

            assertThat(stepExecution.getReadCount())
                    .isZero();

            assertThat(stepExecution.getWriteCount())
                    .isZero();

            verifyNoInteractions(customerProcessor);
            verifyNoInteractions(itemWriter);
        }
    }

    @Nested
    class ListenerBehaviour {

        @Test
        void shouldInvokeStepListener() throws Exception {

            when(reader.read()).thenReturn(null);

            launchStep();

            verify(stepListener, times(2))
                    .beforeStep(any());

            verify(stepListener, times(2))
                    .afterStep(any());
        }

        @Test
        void shouldInvokeChunkListener() throws Exception {

            CustomerCsvRecord csvRecord = mock(CustomerCsvRecord.class);

            when(reader.read())
                    .thenReturn(csvRecord)
                    .thenReturn(null);

            when(customerProcessor.process(csvRecord))
                    .thenReturn(new CustomerEntity());

            launchStep();

            verify(chunkListener, times(2))
                    .beforeStep(any(StepExecution.class));

            verify(chunkListener, times(2))
                    .afterStep(any(StepExecution.class));
        }

        @Test
        void shouldInvokeSkipListener() throws Exception {

            CustomerCsvRecord csvRecord = mock(CustomerCsvRecord.class);

            when(reader.read())
                    .thenReturn(csvRecord)
                    .thenReturn(null);

            when(customerProcessor.process(csvRecord))
                    .thenThrow(new ValidationException("invalid"));

            launchStep();

            verify(skipListener)
                    .onSkipInProcess(
                            eq(csvRecord),
                            any(ValidationException.class));
        }
    }

}
