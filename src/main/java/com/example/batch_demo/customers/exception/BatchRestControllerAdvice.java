package com.example.batch_demo.customers.exception;

import com.example.batch_demo.customers.domain.BatchErrorResponse;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

/**
 * Translates batch launch failures into stable HTTP responses.
 */
@RestControllerAdvice
public class BatchRestControllerAdvice {

    private static final String CUSTOMER_REPORT_JOB = "CustomerReportJob";

    @ExceptionHandler(InvalidJobParametersException.class)
    public ResponseEntity<BatchErrorResponse> handleInvalidParameters(InvalidJobParametersException ex) {
        return build(HttpStatus.BAD_REQUEST, "INVALID_JOB_PARAMETERS", ex.getMessage());
    }

    @ExceptionHandler({
            JobExecutionAlreadyRunningException.class,
            JobInstanceAlreadyCompleteException.class,
            JobRestartException.class
    })
    public ResponseEntity<BatchErrorResponse> handleConflict(Exception ex) {
        return build(HttpStatus.CONFLICT, "BATCH_JOB_CONFLICT", ex.getMessage());
    }

    @ExceptionHandler(NoSuchJobException.class)
    public ResponseEntity<BatchErrorResponse> handleMissingJob(NoSuchJobException ex) {
        return build(HttpStatus.NOT_FOUND, "JOB_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BatchErrorResponse> handleUnexpected(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "BATCH_JOB_ERROR", ex.getMessage());
    }

    private ResponseEntity<BatchErrorResponse> build(HttpStatus status, String error, String message) {
        BatchErrorResponse body = new BatchErrorResponse(
                error,
                message != null ? message : status.getReasonPhrase(),
                CUSTOMER_REPORT_JOB,
                OffsetDateTime.now()
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(CustomerReportJobException.class)
    public ResponseEntity<BatchErrorResponse> handleAlreadyRunning(
            CustomerReportJobException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BatchErrorResponse(
                        ex.getErrorEnum().name(),
                        ex.getMessage(),
                        ex.getJobName(),
                        OffsetDateTime.now()
                ));
    }
}