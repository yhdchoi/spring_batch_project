package com.yhdc.spring_batch.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum ServicePolicy {

    // Test set
    A(1L, "/api/services/a", 10),
    B(2L, "/api/services/b", 10),
    C(3L, "/api/services/c", 10),
    D(4L, "/api/services/d", 10),
    E(5L, "/api/services/e", 10),
    F(6L, "/api/services/f", 15),
    G(7L, "/api/services/g", 15),
    H(8L, "/api/services/h", 10),
    I(9L, "/api/services/i", 10),
    J(10L, "/api/services/j", 12),
    K(11L, "/api/services/k", 12),
    L(12L, "/api/services/l", 10),
    M(13L, "/api/services/m", 10),
    N(14L, "/api/services/n", 17),
    O(15L, "/api/services/o", 17),
    P(16L, "/api/services/p", 10),
    Q(17L, "/api/services/q", 10),
    R(18L, "/api/services/r", 10),
    S(19L, "/api/services/s", 18),
    T(20L, "/api/services/t", 18),
    U(21L, "/api/services/u", 10),
    V(22L, "/api/services/v", 10),
    W(23L, "/api/services/w", 19),
    X(24L, "/api/services/x", 13),
    Y(25L, "/api/services/y", 13),
    Z(26L, "/api/services/z", 10);

    private final Long id;
    private final String url;
    private final Integer fee;

    ServicePolicy(Long id, String url, Integer fee) {
        this.id = id;
        this.url = url;
        this.fee = fee;
    }

    public static ServicePolicy findByUrl(String url) {
        return Arrays.stream(values())
                .filter(data -> data.url.equals(url))
                .findFirst()
                .orElseThrow();
    }

    public static ServicePolicy findById(Long id) {
        return Arrays.stream(values())
                .filter(data -> Objects.equals(data.id, id))
                .findFirst()
                .orElseThrow();
    }

}
