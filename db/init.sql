CREATE TABLE IF NOT EXISTS customers (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    city VARCHAR(100) NOT NULL,
    created_at DATE NOT NULL
    );

CREATE TABLE IF NOT EXISTS customer_import_error (
    id BIGSERIAL PRIMARY KEY,
    raw_line TEXT NOT NULL,
    error_message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS customer_report
(
    customer_id BIGINT PRIMARY KEY,
    full_name   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    city        VARCHAR(100) NOT NULL
);