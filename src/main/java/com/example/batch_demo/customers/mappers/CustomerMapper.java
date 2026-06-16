package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.stereotype.Component;

import static com.example.batch_demo.utils.MappingUtils.trim;

/**
 * Converts CSV records into persistence entities.
 */
@Component
public class CustomerMapper {


    /**
     * Maps a CSV record to a JPA entity.
     *
     * @param source the parsed CSV record
     * @return the persistence entity
     */
    public CustomerEntity toEntity(CustomerCsvRecord source) {
        if (source == null) {
            return null;
        }

        return new CustomerEntity(
                source.id(),
                trim(source.firstName()).toUpperCase(),
                trim(source.lastName()).toUpperCase(),
                trim(source.email()),
                trim(source.city()),
                source.createdAt()
        );
    }

    /**
     * Maps a JPA entity back to a CSV record.
     *
     * @param source the persistence entity
     * @return the CSV record
     */
    public CustomerCsvRecord toCsvRecord(CustomerEntity source) {
        if (source == null) {
            return null;
        }

        return new CustomerCsvRecord(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                source.getCity(),
                source.getCreatedAt()
        );
    }
}
