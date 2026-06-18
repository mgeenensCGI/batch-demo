package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.persistence.entities.CustomerReportEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerReportRowMapper implements RowMapper<CustomerReportEntity> {

    @Override
    public CustomerReportEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerReportEntity entity = new CustomerReportEntity();

        entity.setCustomerId(rs.getLong(CUSTOMER_ID));
        entity.setFullName(rs.getString(FULL_NAME));
        entity.setEmail(rs.getString(EMAIL));
        entity.setCity(rs.getString(CITY));

        return entity;
    }
}
