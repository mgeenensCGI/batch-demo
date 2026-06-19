package com.example.batch_demo.customers.batch.processes.processors;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.mappers.CustomerMapper;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerItemProcessorTest {

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerItemProcessor processor;

    @Test
    void shouldDelegateMappingToCustomerMapper() {
        CustomerCsvRecord input = new CustomerCsvRecord(
                1L,
                "Alice",
                "Dubois",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        CustomerEntity expected = new CustomerEntity(
                1L,
                "ALICE",
                "DUBOIS",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        when(mapper.toEntity(input)).thenReturn(expected);

        CustomerEntity result = processor.process(input);

        assertThat(result).isSameAs(expected);
        verify(mapper).toEntity(input);
    }

}
