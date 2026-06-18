package com.example.batch_demo.customers.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

import static com.example.batch_demo.customers.batch.constants.CustomersConstants.*;

@Entity
@Table(name = CUSTOMERS)
@Data
public class CustomerEntity {

    @Id
    @Column(name = ID, nullable = false)
    private Long id;

    @Column(name = FIRST_NAME, nullable = false, length = 100)
    private String firstName;

    @Column(name = LAST_NAME, nullable = false, length = 100)
    private String lastName;

    @Column(name = EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = CITY, nullable = false, length = 100)
    private String city;

    @Column(name = CREATED_AT, nullable = false)
    private LocalDate createdAt;

    /**
     * Required by JPA.
     */
    protected CustomerEntity() {
    }

    public CustomerEntity(Long id,
                          String firstName,
                          String lastName,
                          String email,
                          String city,
                          LocalDate createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.createdAt = createdAt;
    }
}