package com.pk.ms.dao.year;

import com.pk.ms.entities.year.YearPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearPlanRepository extends CrudRepository<YearPlan, Long> {

    YearPlan findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM year_plan WHERE schedule_id = ? AND year_id = ?")
    List<YearPlan> findYearPlansByScheduleIdAndYearId(long scheduleId, long yearId);

}
