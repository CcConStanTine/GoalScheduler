package com.pk.ms.dao.week;

import com.pk.ms.entities.week.Week;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeekRepository extends CrudRepository<Week, Long> {

    Optional<Week> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE year_id = ?")
    List<Week> findAllByYearId(long yearId);

    @Query(nativeQuery = true, value = "SELECT * FROM week WHERE start_date <= ?1 AND end_date >= ?1")
    Optional<Week> findByDate(LocalDate date);

}
