package com.ll.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class HelloJobConfiguration {
    @Bean
    public Job helloJob(JobRepository jobRepository, Step helloStep1) {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep1)
                .build();
    }

    @Bean
    public Step helloStep1(JobRepository jobRepository, Tasklet testTasklet,
                           PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep1Tasklet", jobRepository)
                .tasklet(testTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet helloStep1Tasklet() {
        return ((contribution, chunkContext) -> {
            log.info("Hello World");
            return RepeatStatus.FINISHED;
        });
    }
}