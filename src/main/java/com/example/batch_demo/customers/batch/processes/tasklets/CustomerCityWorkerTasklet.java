package com.example.batch_demo.customers.batch.processes.tasklets;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class CustomerCityWorkerTasklet implements Tasklet {

    private final String city;

    public CustomerCityWorkerTasklet(@Value("#{stepExecutionContext['city']}") String city) {
        this.city = city;
    }

    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info(
                "Thread={}} | Processing city={}",
                Thread.currentThread().getName(),
                city
        );

        return RepeatStatus.FINISHED;
    }
}
