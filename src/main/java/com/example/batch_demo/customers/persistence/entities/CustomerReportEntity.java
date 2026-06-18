package com.example.batch_demo.customers.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = CUSTOMER_REPORT)
@Data
public class CustomerReportEntity {

    @Id
    @Column(name = CUSTOMER_ID)
    private Long customerId;

    @Column(name = FULL_NAME, nullable = false)
    private String fullName;

    @Column(name = EMAIL, nullable = false)
    private String email;

    @Column(name = CITY, nullable = false)
    private String city;
}
