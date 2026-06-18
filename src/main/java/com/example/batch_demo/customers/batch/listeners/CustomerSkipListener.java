package com.example.batch_demo.customers.batch.listeners;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.batch.utils.CustomersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerSkipListener
        implements SkipListener<CustomerCsvRecord, CustomerEntity> {

    @Override
    public void onSkipInRead(Throwable throwable) {

        log.error(
                "Record skipped during read | exceptionType={} | message={}",
                throwable.getClass().getSimpleName(),
                throwable.getMessage()
        );
    }

    @Override
    public void onSkipInProcess(CustomerCsvRecord item,
                                Throwable throwable) {

        log.warn(
                "Customer skipped during processing | customer={} | exceptionType={} | message={}",
                CustomersUtil.formatCustomerRecord(item),
                throwable.getClass().getSimpleName(),
                throwable.getMessage()
        );
    }

    @Override
    public void onSkipInWrite(CustomerEntity item,
                              Throwable throwable) {

        log.error(
                "Customer skipped during write | customer={} | exceptionType={} | message={}",
                CustomersUtil.formatCustomerEntity(item),
                throwable.getClass().getSimpleName(),
                throwable.getMessage()
        );
    }
}
