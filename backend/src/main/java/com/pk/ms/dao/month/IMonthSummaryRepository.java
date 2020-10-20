package com.pk.ms.dao.month;

import com.pk.ms.entities.month.MonthSummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IMonthSummaryRepository extends CrudRepository<MonthSummary, Long> {

    public MonthSummary findById(long id);

}
