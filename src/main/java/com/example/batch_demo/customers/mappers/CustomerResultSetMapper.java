package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.batch_demo.customers.batch.constants.CustomersConstants.*;

@Slf4j
public class CustomerResultSetMapper {

    private CustomerResultSetMapper() {
    }

    public static CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("rowNum :: {}", rowNum);
        return new CustomerEntity(
                rs.getLong(ID),
                rs.getString(FIRST_NAME),
                rs.getString(LAST_NAME),
                rs.getString(EMAIL),
                rs.getString(CITY),
                rs.getDate(CREATED_AT).toLocalDate()
        );
    }
}
