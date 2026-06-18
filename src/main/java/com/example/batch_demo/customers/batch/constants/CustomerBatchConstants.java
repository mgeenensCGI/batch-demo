package com.example.batch_demo.customers.batch.constants;

public class CustomerBatchConstants {

    private CustomerBatchConstants() {}

    // JOBS
    public static final String CUSTOMERS_IMPORT_JOB_NAME = "customersImportJob";
    public static final String CUSTOMER_REPORT_INSERT_JOB_NAME = "customerReportInsertJob";
    public static final String CUSTOMER_REPORT_EXPORT_JOB_NAME = "customerReportExportJob";

    // STEPS
    public static final String CUSTOMERS_IMPORT_STEP_NAME = "customersImportStep";
    public static final String GENERATE_SUMMARY_REPORT_STEP_NAME = "generateSummaryReportStep";
    public static final String CUSTOMER_REPORT_INSERT_STEP_NAME = "customersReportInsertStep";
    public static final String CUSTOMER_REPORT_EXPORT_STEP_NAME = "customerReportExportStep";

    // PROCESSING
    public static final String CUSTOMERS_CSV_READER_NAME = "customerCsvReader";
    public static final String CUSTOMERS_READER_NAME = "customerReader";
    public static final String CUSTOMER_REPORT_FILE_WRITER_NAME = "customerReportFileWriter";

    // DECIDERS
    public static final String NO_DATA = "NO_DATA";
    public static final String GENERATE_SUMMARY = "GENERATE_SUMMARY";

    // PARAMETERS
    public static final String RUN_ID = "run.id";
    public static final String INPUT_FILE = "inputFile";
    public static final String OUTPUT_PATH = "outputPath";

}
