package com.pk.ms.dao.week;

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

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE year_id = ?1 " +
            "AND (((?2 >= start_date AND ?2 <= end_date) OR (?3 >= start_date AND ?3 <= end_date))" +
            "OR ((start_date >= ?2 AND start_date <= ?3) OR (end_date >= ?2 AND end_date <= ?3)))")
    public Week findYearIncludingDatesBetweenStartDateAndEndDate(long yearId, Date sDate, Date eDate);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE year_id = ?")
    public List<Week> findAllByYearId(long id);

}
