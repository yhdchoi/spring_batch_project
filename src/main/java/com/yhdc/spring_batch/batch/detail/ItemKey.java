package com.yhdc.spring_batch.batch.detail;

import java.io.Serializable;

public record ItemKey(Long customerId, Long serviceId) implements Serializable {
}
