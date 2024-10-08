package com.batch.jobs.config;


import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.jobs.model.SampleData;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job sampleJob(JobBuilderFactory jobBuilderFactory, Step sampleStep) {
        return jobBuilderFactory.get("sampleJob")
                .start(sampleStep)
                .listener(jobExecutionListener())
                .build();
    }

    @Bean
    public Step sampleStep(StepBuilderFactory stepBuilderFactory, ItemReader<SampleData> reader,
                           ItemProcessor<SampleData, SampleData> processor, ItemWriter<SampleData> writer) {
        return stepBuilderFactory.get("sampleStep")
                .<SampleData, SampleData>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<SampleData> reader() {
        return new ListItemReader<>(Arrays.asList(
                new SampleData("Item 1"),
                new SampleData("Item 2"),
                new SampleData("Item 3")
        ));
    }

    @Bean
    public ItemProcessor<SampleData, SampleData> processor() {
        return item -> {
            System.out.println("Processing " + item.getName());
            return item;
        };
    }

    @Bean
    public ItemWriter<SampleData> writer() {
        return items -> items.forEach(item -> System.out.println("Writing " + item.getName()));
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new SimpleJobCompletionListener();
    }
}

