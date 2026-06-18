package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.batch_demo.customers.batch.constants.CustomersConstants.*;

@Component
public class CustomerRowMapper implements RowMapper<CustomerEntity> {

    @Override
    public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

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
