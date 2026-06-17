# Customer Report Job Trigger Plan

## Stack Reminder

- Spring Boot 4.x
- Spring Batch 6.x
- Java 21
- JPA / Hibernate
- PostgreSQL (dockerized)
- Existing batch job and step are already in place

---

## Goal

1. Implement a controller that triggers `CustomerReportJob` through a dedicated service.
2. Add a lightweight Swagger / OpenAPI YAML configuration so the job can be triggered from an API UI.

---

## Current Context

### Existing job

```java
@Configuration
public class CustomerReportJobConfig {

    @Bean
    public Job customerReportJob(JobRepository jobRepository,
                                 Step customersReportInsertStep,
                                 CustomerJobListener listener) {
        return new JobBuilder(CUSTOMER_REPORT_JOB_NAME, jobRepository)
                .listener(listener)
                .start(customersReportInsertStep)
                .build();
    }
}
```

### Existing step

```java
@Configuration
public class CustomersReportInsertStepConfig {

    @Bean
    public Step customersReportInsertStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<CustomerEntity> reader,
            ItemProcessor<CustomerEntity, CustomerReportEntity> processor,
            JdbcBatchItemWriter<CustomerReportEntity> writer,
            CustomerStepListener stepListener) {
        return new StepBuilder(CUSTOMER_REPORT_INSERT_STEP_NAME, jobRepository)
                .<CustomerEntity, CustomerReportEntity>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(stepListener)
                .transactionManager(transactionManager)
                .build();
    }
}
```

### Existing batch settings

```yaml
spring:
  batch:
    jdbc:
      initialize-schema: always
    job:
      enabled: false
```

### Existing dependencies

- `spring-boot-starter-batch`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `spring-boot-starter-webmvc`
- `spring-boot-batch-jdbc`
- PostgreSQL driver
- `spring-batch-test`

---

## Functional Direction

### Keep

- Import job startup behavior can remain separate.
- Customer report job stays triggerable manually.
- Job configuration stays separated from web logic.

### Add

- A dedicated service for report job execution.
- A REST controller that calls the service.
- OpenAPI / Swagger YAML for the report endpoint.

---

## Recommended Architecture

### 1) Controller
Expose a simple endpoint to trigger the report job.

Possible endpoint:

- `POST /api/batch/customer-report/run`

Controller responsibilities:

- receive request
- validate input
- call the service
- return a structured response

Controller should not:
- know job internals
- build `JobParameters`
- decide whether the job can run

---

### 2) Service
Create a dedicated service that owns batch execution rules.

Suggested name:

- `CustomerReportJobService`
- `CustomerReportExecutionService`
- `BatchReportService`

Service responsibilities:

- build job parameters
- launch the `CustomerReportJob`
- map execution result to a response object
- handle execution exceptions
- optionally prevent duplicate or invalid launches

Service should not:
- expose HTTP concerns
- contain Swagger annotations
- know controller details

---

### 3) Trigger policy
Decide whether the report job should:

- always run when called
- refuse if a previous execution is still running
- refuse if the latest execution already completed
- accept a business date / report date parameter

For the first version, the cleanest choice is usually:

- allow manual trigger
- use a unique run parameter
- return execution status

---

### 4) Swagger / OpenAPI
Use a lightweight YAML definition for the manual trigger endpoint.

The YAML should describe:

- endpoint path
- HTTP method
- request body
- response body
- error responses

Keep it minimal.

Do not over-model the API at the beginning.

---

## Proposed Implementation Sequence

### Step 1 — Define the API contract
Decide the request and response shape.

Possible request:
- no body at first
- or a small JSON body with optional metadata

Possible response:
- `jobName`
- `executionId`
- `status`
- `message`
- timestamps

---

### Step 2 — Build the service
Create a service that:

- receives the trigger request
- builds `JobParameters`
- launches `customerReportJob`
- returns execution information

---

### Step 3 — Build the controller
Create a REST controller that:

- exposes the trigger endpoint
- delegates to the service
- returns `200`, `202`, or `409` depending on execution strategy

---

### Step 4 — Add OpenAPI YAML
Create a minimal OpenAPI file.

Suggested location:

- `src/main/resources/openapi/customer-report-api.yaml`

Possible endpoint spec:

- `POST /api/batch/customer-report/run`

---

### Step 5 — Wire Swagger UI
If needed, configure Swagger UI to load the YAML file.

Keep this lightweight and focused on the batch endpoint only.

---

## Where to Put the Job Parameter Logic

The report job parameter creation should live in the service layer.

Reason:
- it is part of execution logic
- it is not controller responsibility
- it can be tested independently

Suggested private method names:

- `buildJobParameters()`
- `launchReportJob()`
- `mapExecutionToResponse()`

---

## What the Controller Should Probably Not Do

- sort files
- discover files
- inspect the job repository directly
- build query or insert logic
- decide batch restart policies

---

## What the Service Should Probably Not Do

- contain web annotations
- return raw HTTP objects
- know Swagger details

---

## Planned Class Split

```text
web/
  CustomerReportController

service/
  CustomerReportExecutionService

dto/
  CustomerReportRunRequest
  CustomerReportRunResponse
  BatchErrorResponse

config/
  CustomerReportOpenApiConfig
  CustomerReportSwaggerConfig
```

---

## Minimal Decision Points for Tomorrow

### A) Request shape
Choose one:

- no request body
- small request body
- request body with optional filters

### B) Trigger semantics
Choose one:

- always launch a new execution
- reject concurrent execution
- reject if last execution is already completed

### C) API style
Choose one:

- pure YAML OpenAPI file
- Swagger annotations plus generated spec
- YAML plus UI loader config

### D) Response style
Choose one:

- simple execution id and status
- richer DTO with timestamps and message

---

## Recommended Default Choices

For a first implementation:

- request body: optional or empty
- trigger semantics: start a fresh execution with unique parameters
- API style: lightweight OpenAPI YAML
- response style: execution id + status + message

This keeps the first version easy to deliver and test.

---

## Questions to Resolve Tomorrow

1. Should the report job accept parameters or run without input?
2. Should concurrent report executions be rejected?
3. Do you want the report endpoint to be sync or async from the client perspective?
4. Do you want manual trigger only, or manual + startup orchestration later?
5. Should the API return execution status immediately or only a launch acknowledgment?

---

## Next Working Output

Tomorrow, the implementation should produce:

- `CustomerReportExecutionService`
- `CustomerReportController`
- `openapi/customer-report-api.yaml`
- optional Swagger UI configuration
- a small set of DTOs
- a test plan for the controller and service

---

## Summary

The clean path is:

1. keep the batch job and step configs as they are
2. add a dedicated execution service for the report job
3. expose a controller to trigger that service
4. document it with a small OpenAPI YAML file
5. keep the import startup behavior separate
