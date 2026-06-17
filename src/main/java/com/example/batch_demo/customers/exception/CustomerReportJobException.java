package com.example.batch_demo.customers.exception;

import lombok.Getter;

@Getter
public class CustomerReportJobException extends RuntimeException {

    private final ErrorEnum errorEnum;
    private final String jobName;

    public CustomerReportJobException(ErrorEnum errorEnum, String jobName, String message) {
        super(message);
        this.errorEnum = errorEnum;
        this.jobName = jobName;
    }

    public CustomerReportJobException(ErrorEnum errorEnum, String jobName, String message, Throwable cause) {
        super(message, cause);
        this.errorEnum = errorEnum;
        this.jobName = jobName;
    }
}
