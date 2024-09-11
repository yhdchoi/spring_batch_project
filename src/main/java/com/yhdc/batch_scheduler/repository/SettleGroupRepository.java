package com.yhdc.batch_scheduler.repository;

import com.yhdc.batch_scheduler.entity.SettleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SettleGroupRepository extends JpaRepository<SettleGroup, UUID> {

    @Query(
            value = """
                    SELECT new SettleGroup(detail.customerId, detail.serviceId, sum(detail.count), sum(detail.fee))
                    FROM SettleDetail detail
                    WHERE detail.targetDate between :start and :end
                    AND detail.customerId = :customerId
                    GROUP BY detail.serviceId
                    """
    )
    List<SettleGroup> findGroupByCustomerIdAndServiceId(LocalDate start, LocalDate end, Long customerId);

}
