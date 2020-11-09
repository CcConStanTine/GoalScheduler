package com.pk.ms.dao.month;

import com.pk.ms.entities.month.Month;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MonthRepository extends CrudRepository<Month, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM month WHERE year_id = ?")
    List<Month> findAllByYearId(long id);

    Month findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM month WHERE year_id = ? AND month_name = ?")
    Month findByYearIdAndMonthNumber(long yearId, int monthNumber);

}
