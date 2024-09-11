package com.yhdc.batch_scheduler.batch.group;

import com.yhdc.batch_scheduler.entity.Customer;
import com.yhdc.batch_scheduler.entity.SettleGroup;
import com.yhdc.batch_scheduler.repository.SettleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SettleGroupProcessor implements ItemProcessor<Customer, List<SettleGroup>>, StepExecutionListener {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final SettleGroupRepository settleGroupRepository;
    private StepExecution stepExecution;

    @Override
    public List<SettleGroup> process(Customer item) throws Exception {
        final String targetDate = stepExecution.getJobParameters().getString("targetDate");
        final LocalDate end = LocalDate.parse(targetDate, formatter);

        return settleGroupRepository.findGroupByCustomerIdAndServiceId(
                end.minusDays(6),
                end,
                item.getId()
        );
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }


}
