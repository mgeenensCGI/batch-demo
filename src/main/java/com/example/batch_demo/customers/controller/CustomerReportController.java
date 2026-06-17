package com.example.batch_demo.customers.controller;

import com.example.batch_demo.customers.domain.JobRunResponse;
import com.example.batch_demo.customers.service.CustomerReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/batch/customer-report")
public class CustomerReportController {

    private final CustomerReportService service;

    public CustomerReportController(CustomerReportService service) {
        this.service = service;
    }

    @PostMapping("/run")
    public ResponseEntity<JobRunResponse> run() {
        JobRunResponse response = this.service.run();
        return ResponseEntity.accepted().body(response);
    }

    @PostMapping("/export")
    public ResponseEntity<JobRunResponse> export() throws IOException {
        JobRunResponse response = this.service.export();
        return ResponseEntity.accepted().body(response);
    }
}
