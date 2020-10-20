package com.pk.ms.dao.day;

import com.pk.ms.entities.day.DayPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDayPlanRepository extends CrudRepository<DayPlan, Long> {

    public DayPlan findById(long id);

}
