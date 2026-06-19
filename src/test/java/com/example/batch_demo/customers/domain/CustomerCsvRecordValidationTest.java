package com.example.batch_demo.customers.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerCsvRecordValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldPassValidationForValidRecord() {
        CustomerCsvRecord customerCsvRecord = new CustomerCsvRecord(
                1L,
                "Alice",
                "Dubois",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        Set<ConstraintViolation<CustomerCsvRecord>> violations = validator.validate(customerCsvRecord);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenEmailIsInvalid() {
        CustomerCsvRecord customerCsvRecord = new CustomerCsvRecord(
                1L,
                "Alice",
                "Dubois",
                "invalid-email",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        Set<ConstraintViolation<CustomerCsvRecord>> violations = validator.validate(customerCsvRecord);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .extracting(Object::toString)
                .contains("email");
    }

    @Test
    void shouldFailValidationWhenFirstNameIsBlank() {
        CustomerCsvRecord customerCsvRecord = new CustomerCsvRecord(
                1L,
                " ",
                "Dubois",
                "alice.dubois001@example.com",
                "Nice",
                LocalDate.of(2024, 1, 2)
        );

        Set<ConstraintViolation<CustomerCsvRecord>> violations = validator.validate(customerCsvRecord);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .extracting(Object::toString)
                .contains("firstName");
    }
}
