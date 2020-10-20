package com.pk.ms.dao.week;

import com.pk.ms.entities.week.WeekPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeekPlanRepository extends CrudRepository<WeekPlan, Long> {

    public WeekPlan findById(long id);

}
