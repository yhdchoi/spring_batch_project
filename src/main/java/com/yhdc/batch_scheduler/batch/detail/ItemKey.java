package com.yhdc.batch_scheduler.batch.detail;

import java.io.Serializable;

public record ItemKey(Long customerId, Long serviceId) implements Serializable {
}
