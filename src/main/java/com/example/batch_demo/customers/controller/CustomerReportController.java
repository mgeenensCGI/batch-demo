package com.example.batch_demo.customers.controller;

import com.example.batch_demo.customers.domain.JobRunResponse;
import com.example.batch_demo.customers.service.CustomerReportExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch/customer-report")
public class CustomerReportController {

    private final CustomerReportExecutionService service;

    public CustomerReportController(CustomerReportExecutionService service) {
        this.service = service;
    }

    @PostMapping("/run")
    public ResponseEntity<JobRunResponse> run() {
        JobRunResponse response = this.service.run();
        return ResponseEntity.accepted().body(response);
    }
}
