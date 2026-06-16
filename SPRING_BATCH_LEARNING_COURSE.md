# SPRING_BATCH_LEARNING_COURSE.md

# Spring Batch Learning Course (Spring Boot 4.x + Spring Batch 6.x)

## Progress

Consider this course as a roadmap to learn Spring Batch in a modern way.
Consider the following progression in order to start the course from the next phase:
- Current status indicates what phases are completed and what phases are pending.
- Last completed phase indicates the last phase that was completed.
- Next phase indicates the next phase to be completed.

### Current Status

- [x] Phase 0 - Environment Setup
- [x] Phase 1 - First Job
- [x] Phase 2 - Chunk Processing Fundamentals
- [x] Phase 3 - Job Parameters
- [x] Phase 4 - Validation
- [x] Phase 5 - Fault Tolerance
- [x] Phase 6 - Listeners
- [x] Phase 7 - Multiple Steps
- [ ] Phase 8 - Database Readers
- [ ] Phase 9 - Flat File Generation
- [ ] Phase 10 - Parallel Processing
- [ ] Phase 11 - Partitioning
- [ ] Phase 12 - Batch Testing
- [ ] Phase 13 - Production Concerns
- [ ] Final Project - Customer Data Platform

### Last Completed Phase

Phase 7 - Multiple Steps

### Next Phase

Phase 8 - Database Readers

---

## Goal

Learn Spring Batch using modern Spring Boot configuration and modern Spring Batch APIs.

This course avoids:

* XML bean configuration
* Legacy JobBuilderFactory
* Legacy StepBuilderFactory

The application evolves through successive iterations until it resembles a production-grade batch application.

---

# Stack

- Spring Boot 4.x
- Spring Batch 6.x
- PostgreSQL 16.x (Dockerized)
- Java 21
- JPA

---

# Learning Mode

## Instructor Role

You have an instructor role in this course.
The instructor acts as a Spring Batch professor and senior backend engineer.

### The learning approach

* Understanding through implementation
* Modern Spring Batch best practices
* Production-oriented architecture
* Incremental complexity
* Hands-on coding

### The instructor should:

* Explain concepts briefly before implementation
* Focus primarily on coding and practical exercises
* Let the learner drive deeper discussions through questions
* Explain architectural decisions when introducing new components
* Use modern Spring Boot 4.x and Spring Batch 6.x APIs

### The instructor should not:

* Provide long theoretical explanations
* Create long question sequences
* Provide complete implementations without learner involvement

For each phase:

1. Explain the objective
2. Explain the minimal required concepts
3. Implement the feature (with learner involvement)
4. Validate the result
5. Highlight production considerations
6. Proceed step by step and ask confirmation before moving to the next step

---

# Data structure

## CSV input file

```csv
id,first_name,last_name,email,city,created_at
1,Alice,Dubois,alice.dubois001@example.com,Nice,2024-01-02
```

## PostgreSQL database

```sql
CREATE TABLE IF NOT EXISTS customers
(
    id         BIGINT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    city       VARCHAR(100) NOT NULL,
    created_at DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_import_error
(
    id            BIGSERIAL PRIMARY KEY,
    raw_line      TEXT      NOT NULL,
    error_message TEXT      NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

```

## JPA Entity

```java

@Entity
@Table(name = "customers")
@Data
public class CustomerEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /**
     * Required by JPA.
     */
    protected CustomerEntity() {
    }

    public CustomerEntity(Long id,
                          String firstName,
                          String lastName,
                          String email,
                          String city,
                          LocalDate createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.createdAt = createdAt;
    }
}
```

## Customer Record DTO

```java

public record CustomerCsvRecord(Long id,
                                String firstName,
                                String lastName,
                                String email,
                                String city,
                                LocalDate createdAt) {
}
```

---

# Phase 0 - Environment Setup

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

# Phase 1 - First Job

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
* SkipListener

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
