package com.example.batch_demo.customers.utils;

public class SqlQueryConstants {

    private SqlQueryConstants() {
    }

    public static final String FETCH_CUSTOMERS_ORDER_BY_ID = """
            SELECT id, first_name, last_name, email, city, created_at
            FROM customers
            ORDER BY id
            """;

    public static final String INSERT_CUSTOMER_REPORT = "INSERT INTO customer_report (customer_id, full_name, email, city) VALUES (:customerId, :fullName, :email, :city)";
}
