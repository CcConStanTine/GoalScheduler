package com.pk.ms.dao.schedule;

import com.pk.ms.entities.schedule.LongTermPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILongTermPlanRepository extends CrudRepository<LongTermPlan, Long> {

    public LongTermPlan findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM long_term_plan WHERE schedule_id = ?")
    public List<LongTermPlan> findAllByScheduleId(long id);

}
