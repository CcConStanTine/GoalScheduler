package com.pk.ms.dao.week;

import com.pk.ms.entities.week.Week;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeekRepository extends CrudRepository<Week, Long> {

    Week findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE year_id = ?")
    List<Week> findAllByYearId(long yearId);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE start_date <= ? AND end_date >= ?")
    Week findByDate(LocalDate date);

}
