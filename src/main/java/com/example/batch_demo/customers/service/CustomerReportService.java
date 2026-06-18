package com.example.batch_demo.customers.service;

import com.example.batch_demo.customers.domain.JobRunResponse;
import com.example.batch_demo.customers.batch.utils.ExportUtils;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.batch_demo.customers.mappers.JobRunResponseMapper.mapExecutionToResponse;
import static com.example.batch_demo.customers.batch.utils.JobParametersUtils.buildJobParameters;

/**
 * Owns the execution rules for the customer report batch job.
 */
@Service
public class CustomerReportService {

    private final Job customerReportInsertJob;
    private final Job customerReportExportJob;
    private final JobService jobService;

    public CustomerReportService(Job customerReportInsertJob, Job customerReportExportJob, JobService jobService) {
        this.customerReportInsertJob = customerReportInsertJob;
        this.customerReportExportJob = customerReportExportJob;
        this.jobService = jobService;
    }

    /**
     * Launches the customer report job with a fresh identifying parameter.
     */
    public JobRunResponse run() {
        JobParameters jobParameters = buildJobParameters();
        JobExecution execution = this.jobService.launchJob(this.customerReportInsertJob, jobParameters);

        return mapExecutionToResponse(execution);
    }

    public JobRunResponse export() throws IOException {
        String fileName = ExportUtils.buildExportFileName(CUSTOMER_REPORT_EXPORT_FILE_NAME, CSV);
        Path exportFile = ExportUtils.buildExportPath(TARGET_MAIN_DIRECTORY, TARGET_REPORTS_DIRECTORY, fileName);
        Files.createDirectories(exportFile.getParent());

        JobParameters jobParameters = buildJobParameters(exportFile.toAbsolutePath().toString());
        JobExecution execution = this.jobService.launchJob(this.customerReportExportJob, jobParameters);

        return mapExecutionToResponse(execution);
    }
}
