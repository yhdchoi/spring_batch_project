package com.yhdc.batch_scheduler.batch.domain;

import lombok.Getter;

@Getter
public enum ServicePolicy {

    // Test set
    A(1L, "/api/services/a", 10),
    B(1L, "/api/services/b", 10),
    C(1L, "/api/services/c", 10),
    D(1L, "/api/services/d", 10),
    E(1L, "/api/services/e", 10),
    F(1L, "/api/services/f", 10),
    G(1L, "/api/services/g", 10),
    H(1L, "/api/services/h", 10),
    I(1L, "/api/services/i", 10),
    J(1L, "/api/services/j", 10),
    K(1L, "/api/services/k", 10),
    L(1L, "/api/services/l", 10),
    M(1L, "/api/services/m", 10),
    N(1L, "/api/services/n", 10),
    O(1L, "/api/services/o", 10),
    P(1L, "/api/services/p", 10),
    Q(1L, "/api/services/q", 10),
    R(1L, "/api/services/r", 10),
    S(1L, "/api/services/s", 10),
    T(1L, "/api/services/t", 10),
    U(1L, "/api/services/u", 10),
    V(1L, "/api/services/v", 10),
    W(1L, "/api/services/w", 10),
    X(1L, "/api/services/x", 10),
    Y(1L, "/api/services/y", 10),
    Z(1L, "/api/services/z", 10);

    private final Long id;
    private final String url;
    private final Integer fee;

    ServicePolicy(Long id, String url, Integer fee) {
        this.id = id;
        this.url = url;
        this.fee = fee;
    }
}
