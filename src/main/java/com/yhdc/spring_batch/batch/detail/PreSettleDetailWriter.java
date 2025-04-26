package com.yhdc.spring_batch.batch.detail;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class PreSettleDetailWriter implements ItemWriter<ItemKey>, StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void write(Chunk<? extends ItemKey> chunk) throws Exception {

        final ConcurrentHashMap<ItemKey, Long> snapShotMap = (ConcurrentHashMap<ItemKey, Long>) stepExecution.getExecutionContext().get("snapShots");
        chunk.forEach(itemKey -> {
            snapShotMap.compute(itemKey, (k, v) -> (v == null) ? 1 : v + 1);
        });
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;

        final ConcurrentHashMap<ItemKey, Long> snapShotMap = new ConcurrentHashMap<>();
        stepExecution.getExecutionContext().put("snapShots", snapShotMap);
    }
}
