package com.example.batch_demo.customers.listeners;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.utils.CustomersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ChunkListener;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;

public class CustomerChunkListener implements ChunkListener<CustomerCsvRecord, CustomerEntity> {

    Logger log = LoggerFactory.getLogger(CustomerChunkListener.class);

    private long chunkIndex = 0;

    @Override
    public void beforeChunk(Chunk<CustomerCsvRecord> chunk) {
        chunkIndex++;

        log.info("Chunk #{} started | Size={} | inputItems={}",
                chunkIndex,
                chunk.size(),
                chunk.getItems()
                        .stream()
                        .map(CustomersUtil::formatCustomerRecord)
                        .toList()
        );
    }

    @Override
    public void afterChunk(Chunk<CustomerEntity> chunk) {
        log.info(
                "Chunk #{} ended successfully | size={} | writtenItems={}",
                chunkIndex,
                chunk.size(),
                chunk.getItems().stream()
                        .map(CustomersUtil::formatCustomerEntity)
                        .toList()
        );
    }

    @Override
    public void onChunkError(Exception exception, Chunk<CustomerEntity> chunk) {
        log.error(
                "Chunk #{} failed | size={} | writtenItems={} | exceptionType={} | message={}",
                chunkIndex,
                chunk.size(),
                chunk.getItems().stream()
                        .map(CustomersUtil::formatCustomerEntity)
                        .toList(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                exception
        );

        if (exception instanceof FlatFileParseException parseException) {
            log.error(
                    "Parse failure details | lineNumber={} | input={}",
                    parseException.getLineNumber(),
                    parseException.getInput()
            );
        }
    }
}