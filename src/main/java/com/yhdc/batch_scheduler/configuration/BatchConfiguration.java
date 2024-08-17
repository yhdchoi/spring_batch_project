package com.yhdc.batch_scheduler.configuration;

import com.yhdc.batch_scheduler.batch.BookAuthorProcessor;
import com.yhdc.batch_scheduler.batch.BookTitleProcessor;
import com.yhdc.batch_scheduler.batch.BookWriter;
import com.yhdc.batch_scheduler.entity.Book;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfiguration {


    @Bean
    public Job bookReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("bookReadJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("bookReaderStep", jobRepository)
                .<Book, Book>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }


    /**
     * Book Writer
     * @return
     */
    @Bean
    @StepScope
    public ItemWriter<Book> writer() {
        return new BookWriter();
    }


    /**
     * Book Processor
     * @return
     */
    @Bean
    @StepScope
    public ItemProcessor<Book, Book> processor() {
        CompositeItemProcessor<Book, Book> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(new BookTitleProcessor(), new BookAuthorProcessor()));
        return processor;
    }


    /**
     * Book Reader
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemReader<Book> reader() {
        return new FlatFileItemReaderBuilder<Book>()
                .name("bookReader")
                .resource(new ClassPathResource("book_data.csv"))
                .delimited()
                .names(new String[]{"title", "author", "publishedYear"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Book.class);
                }})
                .build();
    }


}
