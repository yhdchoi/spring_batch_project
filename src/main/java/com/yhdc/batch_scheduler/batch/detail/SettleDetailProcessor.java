package com.yhdc.batch_scheduler.batch.detail;

import com.yhdc.batch_scheduler.domain.ServicePolicy;
import com.yhdc.batch_scheduler.entity.SettleDetail;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SettleDetailProcessor implements ItemProcessor<KeyAndCount, SettleDetail>, StepExecutionListener {

    private StepExecution stepExecution;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public SettleDetail process(KeyAndCount item) throws Exception {
        final ItemKey itemKey = item.key();
        final ServicePolicy servicePolicy = ServicePolicy.findById(itemKey.serviceId());
        final Long count = item.count();

        final String targetDate = stepExecution.getJobParameters().getString("targetDate");

        return new SettleDetail(itemKey.customerId(), itemKey.serviceId(), count, servicePolicy.getFee() * count, LocalDate.parse(targetDate, formatter));
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
