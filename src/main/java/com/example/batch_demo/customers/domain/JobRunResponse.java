package com.example.batch_demo.customers.domain;

public record JobRunResponse(
        String jobName,
        Long executionId,
        String status,
        String message
) {
}
