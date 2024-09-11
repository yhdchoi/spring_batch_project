package com.yhdc.batch_scheduler.batch;

import com.yhdc.batch_scheduler.batch.support.DateFormatParameterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Configuration
public class SettleJobConfiguration {

    private final JobRepository jobRepository;

    @Bean
    public Job settleJob(
            Step preSettleDetailStep,
            Step settleDetailStep,
            Step settleGroupStep) {
        return new JobBuilder("settleJob", jobRepository)
                .validator(new DateFormatParameterValidator(new String[]{"targetDate"}))
                .start(preSettleDetailStep)
                .next(settleDetailStep)
                // if it's the day for collection
                .next(isFridayDecider())
                // check and execute to group step
                .on("COMPLETED")
                .to(settleGroupStep)
                .build()
                .build();

    }

    public JobExecutionDecider isFridayDecider() {
        return (jobExecution, stepExecution) -> {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            final String targetDate = stepExecution.getJobParameters().getString("targetDate");
            final LocalDate date = LocalDate.parse(targetDate, formatter);

            if (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
                return new FlowExecutionStatus("NOPE");
            }
            return FlowExecutionStatus.COMPLETED;
        };
    }
}
