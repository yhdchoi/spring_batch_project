package com.yhdc.batch_scheduler.batch.detail;

import com.yhdc.batch_scheduler.domain.ApiOrder;
import com.yhdc.batch_scheduler.domain.ServicePolicy;
import org.springframework.batch.item.ItemProcessor;

public class PreSettleDetailProcessor implements ItemProcessor<ApiOrder, ItemKey> {

    @Override
    public ItemKey process(ApiOrder item) throws Exception {

        if (item.getState() == ApiOrder.State.FAIL)
            return null;

        final Long serviceId = ServicePolicy.findByUrl(item.getUrl()).getId();

        return new ItemKey(item.getCustomerId(), serviceId);
    }
}
