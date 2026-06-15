package com.example.batch_demo.customers.domain;

import java.time.LocalDate;

public record CustomerCsvRecord(Long id,
                                String firstName,
                                String lastName,
                                String email,
                                String city,
                                LocalDate createdAt) {
}
