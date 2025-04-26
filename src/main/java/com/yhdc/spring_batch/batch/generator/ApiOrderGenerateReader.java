package com.yhdc.spring_batch.batch.generator;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@StepScope
@Component
public class ApiOrderGenerateReader implements ItemReader<Boolean> {

    private Long totalCount;
    private AtomicLong current;

    public ApiOrderGenerateReader(@Value("#{jobParameters['totalCount']}") String totalCount) {
        this.totalCount = Long.valueOf(totalCount);
        this.current = new AtomicLong(0);
    }


    @Override
    public Boolean read() throws Exception {
        if (current.incrementAndGet() > totalCount) {
            return null;
        }
        return true;
    }

}
