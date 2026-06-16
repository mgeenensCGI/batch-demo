package com.example.batch_demo.customers.utils;

public class CustomerBatchConstants {

    private CustomerBatchConstants() {}

    // JOBS
    public static final String CUSTOMERS_IMPORT_JOB_NAME = "customersImportJob";

    // STEPS
    public static final String CUSTOMERS_IMPORT_STEP_NAME = "customersImportStep";
    public static final String GENERATE_SUMMARY_REPORT_STEP_NAME = "generateSummaryReportStep";

    // PROCESSING
    public static final String CUSTOMERS_CSV_READER_NAME = "customerCsvReader";

    // DECIDERS
    public static final String NO_DATA = "NO_DATA";
    public static final String GENERATE_SUMMARY = "GENERATE_SUMMARY";

}
