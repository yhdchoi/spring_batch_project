package com.yhdc.spring_batch.repository;

import com.yhdc.spring_batch.entity.SettleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettleDetailRepository extends JpaRepository<SettleDetail, UUID> {
}
