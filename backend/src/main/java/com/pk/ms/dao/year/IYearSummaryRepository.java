package com.pk.ms.dao.year;

import com.pk.ms.entities.year.YearSummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IYearSummaryRepository extends CrudRepository<YearSummary, Long> {

    public YearSummary findById(long id);

}
