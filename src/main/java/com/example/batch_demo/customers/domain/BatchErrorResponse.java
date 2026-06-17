package com.example.batch_demo.customers.domain;

import java.time.OffsetDateTime;

public record BatchErrorResponse(
        String error,
        String message,
        String jobName,
        OffsetDateTime timestamp) {
}
