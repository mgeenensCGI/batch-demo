package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void shouldMapCsvRecordToEntityAndNormalizeTextFields() {
        // given
        CustomerCsvRecord source = new CustomerCsvRecord(
                1L,
                " Alice ",
                " Dubois ",
                " alice.dubois001@example.com ",
                " Nice ",
                LocalDate.of(2024, 1, 2)
        );

        // when
        CustomerEntity result = customerMapper.toEntity(source);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("ALICE");
        assertThat(result.getLastName()).isEqualTo("DUBOIS");
        assertThat(result.getEmail()).isEqualTo("alice.dubois001@example.com");
        assertThat(result.getCity()).isEqualTo("Nice");
        assertThat(result.getCreatedAt()).isEqualTo(LocalDate.of(2024, 1, 2));
    }

    @Test
    void shouldReturnNullWhenCsvRecordIsNull() {
        assertThat(customerMapper.toEntity(null)).isNull();
    }

    @Test
    void shouldMapEntityBackToCsvRecord() {
        // given
        CustomerEntity source = new CustomerEntity(
                1L,
                "ALICE",
                "DUBOIS",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        // when
        CustomerCsvRecord result = customerMapper.toCsvRecord(source);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.firstName()).isEqualTo("ALICE");
        assertThat(result.lastName()).isEqualTo("DUBOIS");
        assertThat(result.email()).isEqualTo("alice.dubois001@example.com");
        assertThat(result.city()).isEqualTo("Nice");
        assertThat(result.createdAt()).isEqualTo(LocalDate.of(2024, 1, 2));
    }
}
