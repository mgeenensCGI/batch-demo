package com.example.batch_demo.customers.controller;

import com.example.batch_demo.customers.domain.JobRunResponse;
import com.example.batch_demo.customers.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/cityBatch")
    public ResponseEntity<JobRunResponse> run() {
        JobRunResponse response = this.service.runCityBatch();
        return ResponseEntity.accepted().body(response);
    }
}
