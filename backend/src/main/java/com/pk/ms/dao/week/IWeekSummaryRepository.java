package com.pk.ms.dao.week;

import com.pk.ms.entities.week.WeekSummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface IWeekSummaryRepository extends CrudRepository<WeekSummary, Long> {

    public WeekSummary findById(long id);

}
