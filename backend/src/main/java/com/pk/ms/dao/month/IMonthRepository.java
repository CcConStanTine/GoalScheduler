package com.pk.ms.dao.month;

import com.pk.ms.entities.month.Month;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonthRepository extends CrudRepository<Month, Long> {

    public Month findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM month WHERE year_id = ?")
    public List<Month> findAllByYearId(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM month WHERE year_id = ? AND month_name = ?")
    public Month findByYearIdAndMonthName(long yearId, int monthName);

}
