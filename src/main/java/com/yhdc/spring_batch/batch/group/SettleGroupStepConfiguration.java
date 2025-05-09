package com.yhdc.spring_batch.batch.group;

import com.yhdc.spring_batch.entity.Customer;
import com.yhdc.spring_batch.entity.SettleGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SettleGroupStepConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step settleGroupStep(
            SettleGroupReader settleGroupReader,
            SettleGroupProcessor settleGroupProcessor,
            ItemWriter<List<SettleGroup>> settleGroupItemWriter
    ) {
        return new StepBuilder("settleGroupStep", jobRepository)
                .<Customer, List<SettleGroup>>chunk(100, platformTransactionManager)
                .reader(settleGroupReader)
                .processor(settleGroupProcessor)
                .writer(settleGroupItemWriter)
                .build();
    }

    // Composite Pattern
    @Bean
    public ItemWriter<List<SettleGroup>> settleGroupItemWriter(
            SettleGroupItemDBWriter settleGroupItemDbWriter,
            SettleGroupItemMailWriter settleGroupItemMailWriter
    ) {
        return new CompositeItemWriter<>(
                settleGroupItemDbWriter,
                settleGroupItemMailWriter
        );
    }

}
