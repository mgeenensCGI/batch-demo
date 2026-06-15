package com.example.batch_demo.customers.persistence.repositories;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
