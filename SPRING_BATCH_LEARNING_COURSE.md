# SPRING_BATCH_LEARNING_COURSE.md

# Spring Batch Learning Course (Spring Boot 4.x + Spring Batch 6.x)

## Goal

Learn Spring Batch using modern Spring Boot configuration.

This course avoids:
- XML bean configuration
- Legacy JobBuilderFactory
- Legacy StepBuilderFactory

The application evolves through successive iterations until it resembles a production-grade batch application.

---

# Phase 0 - Environment Setup

## Objective

Understand the infrastructure required by Spring Batch.

## Topics

- Spring Batch architecture
- Batch metadata tables
- JobRepository
- JobExecution
- StepExecution
- PostgreSQL integration
- Dockerized database

## Practical Work

Create:

- Spring Boot application
- PostgreSQL container
- Batch metadata schema
- Business schema

Verify:

- application starts
- Spring Batch tables are created

Expected Outcome:

Understand what Spring Batch stores internally.

---

# Phase 1 - First Job

## Objective

Understand the core concepts.

## Topics

- Job
- Step
- Tasklet
- JobRepository
- Transaction Manager

## Practical Work

Create:

HelloWorldJob

Step:

Print:

Hello Spring Batch

using a Tasklet.

Expected Outcome:

Understand:

Job
→ Step
→ Tasklet

execution flow.

---

# Phase 2 - Chunk Processing Fundamentals

## Objective

Learn the most important Spring Batch pattern.

## Topics

- ItemReader
- ItemProcessor
- ItemWriter
- Chunk Processing

## Practical Work

Implement:

CustomerImportJob

Reader:

Read customers.csv

Processor:

Uppercase first and last names.

Writer:

Store data in PostgreSQL.

Expected Outcome:

Understand:

Read
→ Process
→ Write

lifecycle.

---

# Phase 3 - Job Parameters

## Objective

Make jobs reusable.

## Topics

- JobParameters
- JobInstance
- JobExecution
- Restartability

## Practical Work

Pass:

inputFile

as parameter.

Example:

customers.csv

customers_v2.csv

Expected Outcome:

Understand how Spring Batch identifies job executions.

---

# Phase 4 - Validation

## Objective

Introduce business validation.

## Topics

- Jakarta Validation
- Custom validation
- Validation exceptions

## Practical Work

Reject:

- invalid email
- missing first name

Expected Outcome:

Understand validation strategies.

---

# Phase 5 - Fault Tolerance

## Objective

Handle failures gracefully.

## Topics

- Skip
- Retry
- SkipLimit
- RetryLimit

## Practical Work

Create invalid records.

Configure:

- skip invalid rows
- continue processing

Expected Outcome:

Understand resilient processing.

---

# Phase 6 - Listeners

## Objective

Observe execution.

## Topics

- JobExecutionListener
- StepExecutionListener
- ChunkListener

## Practical Work

Log:

- start time
- end time
- number of processed rows

Expected Outcome:

Understand monitoring hooks.

---

# Phase 7 - Multiple Steps

## Objective

Chain business processes.

## Topics

- Sequential steps
- Step transitions

## Practical Work

Step 1:

Import customers.

Step 2:

Generate summary report.

Expected Outcome:

Understand job orchestration.

---

# Phase 8 - Database Readers

## Objective

Read directly from databases.

## Topics

- JdbcCursorItemReader
- JdbcPagingItemReader

## Practical Work

Read:

customers table

Write:

customer_report table

Expected Outcome:

Understand database-to-database processing.

---

# Phase 9 - Flat File Generation

## Objective

Produce export files.

## Topics

- FlatFileItemWriter

## Practical Work

Generate:

customers_export.csv

Expected Outcome:

Understand outbound integrations.

---

# Phase 10 - Parallel Processing

## Objective

Improve performance.

## Topics

- TaskExecutor
- Multi-threaded Step

## Practical Work

Process large CSV files.

Measure execution time.

Expected Outcome:

Understand concurrent execution.

---

# Phase 11 - Partitioning

## Objective

Scale large workloads.

## Topics

- Partitioner
- Worker Step
- Master Step

## Practical Work

Split customer imports by region.

Expected Outcome:

Understand horizontal scaling.

---

# Phase 12 - Batch Testing

## Objective

Test batch applications.

## Topics

- spring-batch-test
- JobLauncherTestUtils

## Practical Work

Test:

- Reader
- Processor
- Writer
- Job

Expected Outcome:

Write reliable batch tests.

---

# Phase 13 - Production Concerns

## Objective

Prepare for enterprise environments.

## Topics

- Idempotency
- Restartability
- Logging
- Metrics
- Monitoring
- Error handling

## Practical Work

Simulate:

- crash during processing
- restart execution

Expected Outcome:

Understand real-world batch design.

---

# Final Project

## Customer Data Platform

Features:

- CSV import
- Validation
- Error management
- Database persistence
- CSV export
- Reporting step
- Parallel processing
- Partitioning
- Full test suite

Deliverables:

- Production-ready batch application
- PostgreSQL database
- Docker environment
- Monitoring hooks

Completion Level:

Junior → Intermediate Spring Batch Developer