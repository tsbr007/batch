package com.batch.jobs.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class SimpleJobCompletionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job is starting...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Job has finished.");
    }
}
