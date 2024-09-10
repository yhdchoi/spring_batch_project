package com.yhdc.batch_scheduler.repository;

import com.yhdc.batch_scheduler.entity.SettleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettleDetailRepository extends JpaRepository<SettleDetail, UUID> {
}
