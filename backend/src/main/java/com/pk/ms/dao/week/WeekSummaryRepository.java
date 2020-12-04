package com.pk.ms.dao.week;

import com.pk.ms.entities.week.WeekSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface WeekSummaryRepository extends CrudRepository<WeekSummary, Long> {

    Optional<WeekSummary> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM week_summary WHERE schedule_id = ? AND week_id = ?")
    Optional<WeekSummary> findByScheduleIdAndWeekId(long scheduleId, long weekId);

}
