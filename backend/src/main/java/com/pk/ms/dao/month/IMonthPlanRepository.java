package com.pk.ms.dao.month;

import com.pk.ms.entities.month.MonthPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonthPlanRepository extends CrudRepository<MonthPlan, Long> {

    public MonthPlan findById(long id);

}
