package com.example.batch_demo.customers.batch.utils;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;

public class CustomersUtil {

    private CustomersUtil() {
    }

    public static String formatCustomerRecord(CustomerCsvRecord item) {
        return "id=" + item.id() + ", email=" + item.email();
    }

    public static String formatCustomerEntity(CustomerEntity item) {
        return "id=" + item.getId() + ", email=" + item.getEmail();
    }
}
