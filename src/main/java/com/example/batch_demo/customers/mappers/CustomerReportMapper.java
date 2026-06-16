package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;

public class CustomerReportMapper {

    private CustomerReportMapper() {
    }

    public static CustomerReportEntity customerToCustomerReport(CustomerEntity customer) throws Exception {
        CustomerReportEntity report = new CustomerReportEntity();
        report.setCustomerId(customer.getId());
        report.setFullName(customer.getFirstName() + " " + customer.getLastName());
        report.setEmail(customer.getEmail());
        report.setCity(customer.getCity());
        return report;
    }
}
