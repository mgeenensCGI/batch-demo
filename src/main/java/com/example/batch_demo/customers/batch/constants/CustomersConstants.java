package com.example.batch_demo.customers.batch.constants;

import java.util.List;

public class CustomersConstants {

    private CustomersConstants() {
    }

    // Snake Case
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String FULL_NAME = "full_name";
    public static final String CREATED_AT = "created_at";
    public static final String CUSTOMER_REPORT = "customer_report";
    public static final String CUSTOMER_ID = "customer_id";

    // Camel Case
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String CREATEDAT = "createdAt";
    public static final String CUSTOMERID = "customerId";
    public static final String FULLNAME = "fullName";

    // Common
    public static final String CUSTOMERS = "customers";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String CITY = "city";

    // Flat file headers
    public static final String CUSTOMER_REPORT_FILE_HEADER = String.join(",", CUSTOMER_ID, FULL_NAME, EMAIL, CITY);

    // Cities
    public static final List<String> CITIES = List.of("Lyon", "Lille", "Nice", "Tokyo", "Paris", "Amsterdam");
}
