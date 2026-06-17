package com.example.batch_demo.customers.utils;

public class CustomerBatchConstants {

    private CustomerBatchConstants() {}

    // JOBS
    public static final String CUSTOMER_WORKFLOW_JOB_NAME = "customerWorkflowJob";
    public static final String CUSTOMERS_IMPORT_JOB_NAME = "customersImportJob";
    public static final String CUSTOMER_REPORT_JOB_NAME = "customerReportJob";

    // JOB STEPs
    public static final String CUSTOMER_IMPORT_JOB_STEP_NAME = "customersImportJobStep";
    public static final String CUSTOMER_REPORT_JOB_STEP_NAME = "customerReportJobStep";

    // STEPS
    public static final String CUSTOMERS_IMPORT_STEP_NAME = "customersImportStep";
    public static final String GENERATE_SUMMARY_REPORT_STEP_NAME = "generateSummaryReportStep";
    public static final String CUSTOMER_REPORT_INSERT_STEP_NAME = "customersReportInsertStep";

    // PROCESSING
    public static final String CUSTOMERS_CSV_READER_NAME = "customerCsvReader";
    public static final String CUSTOMERS_READER_NAME = "customerReader";

    // DECIDERS
    public static final String NO_DATA = "NO_DATA";
    public static final String GENERATE_SUMMARY = "GENERATE_SUMMARY";

    // PARAMETERS
    public static final String RUN_ID = "run.id";

}
