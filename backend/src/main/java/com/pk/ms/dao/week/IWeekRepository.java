package com.pk.ms.dao.week;

import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IWeekRepository extends CrudRepository<Week, Long> {

    public Week findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE year_id = ? AND start_date <= ? AND end_date >= ?")
    public Week findByYearIdAndDate(long yearId, Date sDate, Date eDate);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE year_id = ?")
    public List<Week> findAllByYearId(long id);

}
