package com.yhdc.batch_scheduler.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApiOrder {

    public String id;
    public Long customerId;
    public String url;
    public State state;
    public String createdAt;

    public enum State {
        SUCCESS, FAIL
    }

    public ApiOrder(String id, Long customerId, String url, State state, String createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.url = url;
        this.state = state;
        this.createdAt = createdAt;
    }
}
