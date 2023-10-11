package br.com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job imprimeOlaJob() {

        return jobBuilderFactory
                .get("imprimeOlaJob")
                .start(imprimeOlaStep())
                .build();
    }

    private Step imprimeOlaStep() {

        return stepBuilderFactory
                .get("imprimeOlaStep")
                .tasklet((stepContribution, chunkContext) -> {

                    System.out.println(":::: Óla, mundo! ::::");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
