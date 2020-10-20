package com.pk.ms.dao.day;

import com.pk.ms.entities.day.DaySummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDaySummaryRepository extends CrudRepository<DaySummary, Long> {

    public DaySummary findById(long id);

}
