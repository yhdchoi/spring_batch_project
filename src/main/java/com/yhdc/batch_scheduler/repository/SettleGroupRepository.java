package com.yhdc.batch_scheduler.repository;

import com.yhdc.batch_scheduler.entity.SettleDetail;
import com.yhdc.batch_scheduler.entity.SettleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettleGroupRepository extends JpaRepository<SettleGroup, UUID> {
}
