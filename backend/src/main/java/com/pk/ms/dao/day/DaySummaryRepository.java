package com.pk.ms.dao.day;

import com.pk.ms.entities.day.DaySummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DaySummaryRepository extends CrudRepository<DaySummary, Long> {

    Optional<DaySummary> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day_summary WHERE schedule_id = ? AND day_id = ?")
    Optional<DaySummary> findByScheduleIdAndDayId(long scheduleId, long dayId);

}
