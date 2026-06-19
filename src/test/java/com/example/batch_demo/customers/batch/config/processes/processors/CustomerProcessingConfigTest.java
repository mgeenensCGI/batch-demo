package com.example.batch_demo.customers.batch.config.processes.processors;

import com.example.batch_demo.customers.batch.processes.processors.CustomerItemProcessor;
import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.validator.BeanValidatingItemProcessor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerProcessingConfigTest {

    @Test
    void shouldBuildCompositeProcessorWithValidationThenMapping() throws Exception {
        BeanValidatingItemProcessor<CustomerCsvRecord> validationProcessor = mock(BeanValidatingItemProcessor.class);
        CustomerItemProcessor mappingProcessor = mock(CustomerItemProcessor.class);

        CustomerCsvRecord input = new CustomerCsvRecord(
                1L,
                "Alice",
                "Dubois",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        CustomerEntity output = new CustomerEntity(
                1L,
                "ALICE",
                "DUBOIS",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        when(validationProcessor.process(input)).thenReturn(input);
        when(mappingProcessor.process(input)).thenReturn(output);

        CustomerProcessingConfig config = new CustomerProcessingConfig();

        ItemProcessor<CustomerCsvRecord, CustomerEntity> processor =
                config.customerProcessor(validationProcessor, mappingProcessor);

        CustomerEntity result = processor.process(input);

        assertThat(result).isEqualTo(output);
        verify(validationProcessor).process(input);
        verify(mappingProcessor).process(input);
    }
}
