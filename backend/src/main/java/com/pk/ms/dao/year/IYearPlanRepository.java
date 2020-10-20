package com.pk.ms.dao.year;

import com.pk.ms.entities.year.YearPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IYearPlanRepository extends CrudRepository<YearPlan, Long> {

    public YearPlan findById(long id);

}
