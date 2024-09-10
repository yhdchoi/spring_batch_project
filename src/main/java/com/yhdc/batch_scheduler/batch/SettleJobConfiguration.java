package com.yhdc.batch_scheduler.batch;

import com.yhdc.batch_scheduler.batch.support.DateFormatParameterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SettleJobConfiguration {

    private final JobRepository jobRepository;

    @Bean
    public Job settleJob(Step preSettleDetailStep
            , Step settleDetailStep) {
        return new JobBuilder("settleJob", jobRepository)
                .validator(new DateFormatParameterValidator(new String[]{"targetDate"}))
                .start(preSettleDetailStep)
                .next(settleDetailStep)
                .build();

    }
}
