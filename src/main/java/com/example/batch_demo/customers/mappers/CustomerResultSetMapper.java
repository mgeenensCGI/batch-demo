package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerResultSetMapper {

    private CustomerResultSetMapper() {
    }

    public static CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
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
