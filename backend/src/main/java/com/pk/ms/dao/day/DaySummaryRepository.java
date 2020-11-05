package com.pk.ms.dao.day;

import com.pk.ms.entities.day.DaySummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaySummaryRepository extends CrudRepository<DaySummary, Long> {

    DaySummary findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day_summary WHERE schedule_id = ? AND day_id = ?")
    DaySummary findByScheduleIdAndDayId(long scheduleId, long dayId);

}
