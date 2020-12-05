package com.pk.ms.dao.schedule;

import com.pk.ms.entities.schedule.LongTermPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LongTermPlanRepository extends CrudRepository<LongTermPlan, Long> {

    Optional<LongTermPlan> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM long_term_plan WHERE schedule_id = ?")
    List<LongTermPlan> findAllByScheduleId(long id);

}
