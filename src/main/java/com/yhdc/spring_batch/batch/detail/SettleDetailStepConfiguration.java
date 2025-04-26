package com.yhdc.spring_batch.batch.detail;

import com.yhdc.spring_batch.domain.ApiOrder;
import com.yhdc.spring_batch.entity.SettleDetail;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class SettleDetailStepConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    // Step1: For each file, collect and sort customer & service then insert to Execution Context
    @Bean
    public Step preSettleDetailStep(FlatFileItemReader<ApiOrder> preSettleDetailReader,
                                    PreSettleDetailWriter preSettleDetailWriter,
                                    ExecutionContextPromotionListener executionContextPromotionListener) {

        return new StepBuilder("preSettleDetailStep", jobRepository)
                .<ApiOrder, ItemKey>chunk(5000, platformTransactionManager)
                .reader(preSettleDetailReader)
                .processor(new PreSettleDetailProcessor())
                .writer(preSettleDetailWriter)
                .listener(executionContextPromotionListener)
                .build();
    }


    @StepScope
    @Bean
    public FlatFileItemReader<ApiOrder> preSettleDetailReader(@Value("#{jobParameter['targetDate']}") String targetDate) {

        final String fileName = targetDate + "_api_orders.csv";

        return new FlatFileItemReaderBuilder<ApiOrder>()
                .name("preSettleDetailReader")
                .resource(new ClassPathResource("/datas/" + fileName))
                .linesToSkip(1)
                .delimited()
                .names("id", "customerId", "url", "state", "createdAt")
                .targetType(ApiOrder.class)
                .build();
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        final ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[]{"snapShots"});
        return listener;
    }

    // Step2: Write the data in Execution Context to DB
    @Bean
    public Step settleDetailStep(SettleDetailReader settleDetailReader,
                                 SettleDetailProcessor settleDetailProcessor,
                                 JpaItemWriter<SettleDetail> settleDetailJpaItemWriter) {
        return new StepBuilder("settleDetailStep", jobRepository)
                .<KeyAndCount, SettleDetail>chunk(1000, platformTransactionManager)
                .reader(settleDetailReader)
                .processor(settleDetailProcessor)
                .writer(settleDetailJpaItemWriter)
                .build();
    }

    @Bean
    public JpaItemWriter<SettleDetail> settleDetailWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<SettleDetail>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
