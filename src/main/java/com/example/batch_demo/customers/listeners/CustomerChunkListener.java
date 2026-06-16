package com.example.batch_demo.customers.listeners;

import com.example.batch_demo.customers.domain.CustomerCsvRecord;
import com.example.batch_demo.customers.persistence.entities.CustomerEntity;
import com.example.batch_demo.customers.utils.CustomersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ChunkListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import static com.example.batch_demo.utils.LogUtils.getRootCause;

@Slf4j
@Component
public class CustomerChunkListener implements ChunkListener<CustomerCsvRecord, CustomerEntity>, StepExecutionListener {

    private long chunkIndex = 0L;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.chunkIndex = 0L;
    }

    @Override
    public void beforeChunk(Chunk<CustomerCsvRecord> chunk) {
        chunkIndex++;

        log.info("Chunk #{} started | size={}", chunkIndex, chunk.size());

        if (log.isDebugEnabled()) {
            log.debug("Chunk #{} inputItems={}",
                    chunkIndex,
                    chunk.getItems().stream()
                            .map(CustomersUtil::formatCustomerRecord)
                            .toList());
        }
    }

    @Override
    public void afterChunk(Chunk<CustomerEntity> chunk) {
        log.info("Chunk #{} completed | size={}", chunkIndex, chunk.size());

        if (log.isDebugEnabled()) {
            log.debug("Chunk #{} writtenItems={}",
                    chunkIndex,
                    chunk.getItems().stream()
                            .map(CustomersUtil::formatCustomerEntity)
                            .toList());
        }
    }

    @Override
    public void onChunkError(Exception exception, Chunk<CustomerEntity> chunk) {
        Throwable rootCause = getRootCause(exception);

        log.error("Chunk #{} failed | size={} | rootCauseType={} | rootCauseMessage={}",
                chunkIndex,
                chunk.size(),
                rootCause.getClass().getSimpleName(),
                rootCause.getMessage(),
                exception);

        if (rootCause instanceof FlatFileParseException parseException) {
            log.error("Parse failure details | lineNumber={} | input={}",
                    parseException.getLineNumber(),
                    parseException.getInput());
        }
    }
}