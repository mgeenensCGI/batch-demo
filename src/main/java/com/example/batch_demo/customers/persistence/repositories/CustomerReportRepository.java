package com.example.batch_demo.customers.persistence.repositories;

import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReportRepository extends JpaRepository<CustomerReportEntity, Long> {
}
