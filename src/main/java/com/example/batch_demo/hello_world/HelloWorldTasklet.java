package com.example.batch_demo.hello_world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * Simple tasklet used to prove the execution flow of Spring Batch.
 * It runs once, prints a message, then finishes.
 */
@Component
public class HelloWorldTasklet implements Tasklet {

    Logger log = LoggerFactory.getLogger(HelloWorldTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("Hello, World!");

        return RepeatStatus.FINISHED;
    }
}
