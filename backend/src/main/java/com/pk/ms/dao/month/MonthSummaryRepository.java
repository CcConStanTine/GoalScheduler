package com.pk.ms.dao.month;

import com.pk.ms.entities.month.MonthSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface MonthSummaryRepository extends CrudRepository<MonthSummary, Long> {

    MonthSummary findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM month_summary WHERE schedule_id = ? AND month_id = ?")
    MonthSummary findByScheduleIdAndMonthId(long scheduleId, long monthId);

}
