# SPRING_BATCH_LEARNING_COURSE.md

# Spring Batch Learning Course (Spring Boot 4.x + Spring Batch 6.x)

## Progress

### Current Status

- [x] Phase 0 - Environment Setup
- [x] Phase 1 - First Job
- [ ] Phase 2 - Chunk Processing Fundamentals
- [ ] Phase 3 - Job Parameters
- [ ] Phase 4 - Validation
- [ ] Phase 5 - Fault Tolerance
- [ ] Phase 6 - Listeners
- [ ] Phase 7 - Multiple Steps
- [ ] Phase 8 - Database Readers
- [ ] Phase 9 - Flat File Generation
- [ ] Phase 10 - Parallel Processing
- [ ] Phase 11 - Partitioning
- [ ] Phase 12 - Batch Testing
- [ ] Phase 13 - Production Concerns
- [ ] Final Project - Customer Data Platform

### Last Completed Phase

Phase 1 - First Job

### Next Phase

Phase 2 - Chunk Processing Fundamentals

### Current Knowledge Acquired

- Spring Batch architecture overview
- Job / Step / Tasklet hierarchy
- JobRepository role
- TransactionManager role
- Batch metadata persistence
- JobInstance vs JobExecution
- StepExecution lifecycle
- Batch statuses (STARTING, STARTED, COMPLETED, FAILED, STOPPED)
- Restartability fundamentals
- RunIdIncrementer usage
- Automatic job launching with Spring Boot
- JobInstanceAlreadyCompleteException troubleshooting

---

## Goal

Learn Spring Batch using modern Spring Boot configuration and modern Spring Batch APIs.

This course avoids:

* XML bean configuration
* Legacy JobBuilderFactory
* Legacy StepBuilderFactory

The application evolves through successive iterations until it resembles a production-grade batch application.

---

# Learning Mode

## Instructor Role

The instructor acts as a Spring Batch professor and senior backend engineer.

The learning approach prioritizes:

* Understanding through implementation
* Modern Spring Batch best practices
* Production-oriented architecture
* Incremental complexity
* Hands-on coding

The instructor should:

* Explain concepts briefly before implementation
* Focus primarily on coding and practical exercises
* Avoid excessive theory and long question sequences
* Let the learner drive deeper discussions through questions
* Explain architectural decisions when introducing new components
* Use modern Spring Boot 4.x and Spring Batch 6.x APIs

For each phase:

1. Explain the objective
2. Explain the minimal required concepts
3. Implement the feature
4. Validate the result
5. Highlight production considerations

---

# Phase 0 - Environment Setup ✅ COMPLETED

## Status

Completed.

## Topics Covered

* Spring Batch architecture
* Batch metadata tables
* JobRepository
* PostgreSQL integration
* Batch infrastructure overview

## Outcome

Understood:

* Why Spring Batch requires metadata tables
* Role of JobRepository
* Role of Spring Batch infrastructure

---

# Phase 1 - First Job ✅ COMPLETED

## Status

Completed.

## Topics Covered

* Job
* Step
* Tasklet
* JobRepository
* Transaction Manager
* JobInstance
* JobExecution
* StepExecution
* Batch statuses
* Restartability basics

## Practical Work Completed

Implemented:

HelloWorldJob

with:

HelloWorldStep

and:

HelloWorldTasklet

Verified:

* Job startup
* Tasklet execution
* Metadata persistence
* JobInstanceAlreadyCompleteException behavior
* RunIdIncrementer usage

## Outcome

Understood:

Job
→ Step
→ Tasklet

execution flow.

Understood:

JobInstance
→ JobExecution

relationship.

---

# Phase 2 - Chunk Processing Fundamentals

## Objective

Learn the most important Spring Batch pattern.

## Topics

* Chunk-oriented processing
* ItemReader
* ItemProcessor
* ItemWriter
* Chunk size
* Transaction boundaries
* Commit cycles

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

Understand:

Chunk
→ Transaction
→ Commit

lifecycle.

---

# Phase 3 - Job Parameters

## Objective

Make jobs reusable.

## Topics

* JobParameters
* JobInstance
* JobExecution
* Restartability
* Identifying parameters

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

* Jakarta Validation
* Custom validation
* Validation exceptions

## Practical Work

Reject:

* invalid email
* missing first name

Expected Outcome:

Understand validation strategies.

---

# Phase 5 - Fault Tolerance

## Objective

Handle failures gracefully.

## Topics

* Skip
* Retry
* SkipLimit
* RetryLimit

## Practical Work

Create invalid records.

Configure:

* skip invalid rows
* continue processing

Expected Outcome:

Understand resilient processing.

---

# Phase 6 - Listeners

## Objective

Observe execution.

## Topics

* JobExecutionListener
* StepExecutionListener
* ChunkListener

## Practical Work

Log:

* start time
* end time
* number of processed rows

Expected Outcome:

Understand monitoring hooks.

---

# Phase 7 - Multiple Steps

## Objective

Chain business processes.

## Topics

* Sequential steps
* Step transitions

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

* JdbcCursorItemReader
* JdbcPagingItemReader

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

* FlatFileItemWriter

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

* TaskExecutor
* Multi-threaded Step

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

* Partitioner
* Worker Step
* Master Step

## Practical Work

Split customer imports by region.

Expected Outcome:

Understand horizontal scaling.

---

# Phase 12 - Batch Testing

## Objective

Test batch applications.

## Topics

* spring-batch-test
* JobLauncherTestUtils

## Practical Work

Test:

* Reader
* Processor
* Writer
* Job

Expected Outcome:

Write reliable batch tests.

---

# Phase 13 - Production Concerns

## Objective

Prepare for enterprise environments.

## Topics

* Idempotency
* Restartability
* Logging
* Metrics
* Monitoring
* Error handling

## Practical Work

Simulate:

* crash during processing
* restart execution

Expected Outcome:

Understand real-world batch design.

---

# Final Project

## Customer Data Platform

Features:

* CSV import
* Validation
* Error management
* Database persistence
* CSV export
* Reporting step
* Parallel processing
* Partitioning
* Full test suite

Deliverables:

* Production-ready batch application
* PostgreSQL database
* Docker environment
* Monitoring hooks

Completion Level:

Junior → Intermediate Spring Batch Developer
