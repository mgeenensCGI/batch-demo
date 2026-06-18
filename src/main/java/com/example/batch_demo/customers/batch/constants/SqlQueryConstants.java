package com.example.batch_demo.customers.batch.constants;

public class SqlQueryConstants {

    private SqlQueryConstants() {
    }

    // CUSTOMERS
    public static final String FETCH_CUSTOMERS_ORDER_BY_ID = """
            SELECT id, first_name, last_name, email, city, created_at
            FROM customers
            ORDER BY id
            """;
    public static final String FETCH_ALL_CUSTOMERS_SELECT_CLAUSE = """
            id,
            first_name,
            last_name,
            email,
            city,
            created_at
            """;
    public static final String FETCH_ALL_CUSTOMERS_BY_CITY_WHERE_CLAUSE = "city = '%s'";
    public static final String FETCH_CUSTOMER_FROM_CLAUSE = """
            customers
            """;

    // CUSTOMER REPORT
    public static final String FETCH_CUSTOMER_REPORT_SELECT_CLAUSE = """
                customer_id,
                full_name,
                email,
                city
            """;
    public static final String FETCH_CUSTOMER_REPORT_FROM_CLAUSE = """
                FROM customer_report
            """;

    public static final String INSERT_CUSTOMER_REPORT = "INSERT INTO customer_report (customer_id, full_name, email, city) VALUES (:customerId, :fullName, :email, :city)";
}
