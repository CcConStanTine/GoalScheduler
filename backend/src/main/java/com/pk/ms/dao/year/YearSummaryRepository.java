package com.pk.ms.dao.year;

import com.pk.ms.entities.year.YearSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface YearSummaryRepository extends CrudRepository<YearSummary, Long> {

    YearSummary findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM year_summary WHERE schedule_id = ? AND year_id = ?")
    YearSummary findByScheduleIdAndYearId(long scheduleId, long yearId);

}
