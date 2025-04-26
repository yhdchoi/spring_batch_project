package com.yhdc.spring_batch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "settle_group")
@Entity
public class SettleGroup {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "total_count")
    private Long totalCount;

    @Column(name = "total_fee")
    private Long totalFee;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public SettleGroup(Long customerId, Long serviceId, Long totalCount, Long totalFee) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.totalCount = totalCount;
        this.totalFee = totalFee;
        this.createdAt = LocalDateTime.now();
    }
}
