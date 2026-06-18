package com.example.batch_demo.customers.mappers;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;

import static com.example.batch_demo.customers.batch.constants.CustomersConstants.*;
import static com.example.batch_demo.utils.DateUtils.ISO_LOCAL_DATE_FORMATTER;
import static com.example.batch_demo.utils.DateUtils.parseDate;

public class CustomerCsvRecordFieldSetMapper implements FieldSetMapper<CustomerCsvRecord> {

    @Override
    public CustomerCsvRecord mapFieldSet(FieldSet fieldSet) {
        return new CustomerCsvRecord(
                fieldSet.readLong(ID),
                fieldSet.readString(FIRST_NAME),
                fieldSet.readString(LAST_NAME),
                fieldSet.readString(EMAIL),
                fieldSet.readString(CITY),
                parseDate(fieldSet.readString(CREATED_AT), CREATED_AT, ISO_LOCAL_DATE_FORMATTER)
        );
    }
}
