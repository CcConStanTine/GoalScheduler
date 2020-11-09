package com.pk.ms.dao.day;

import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {

    Day findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE month_id = ?")
    List<Day> findAllByMonthId(long monthId);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE day_date = ?")
    Day findByDate(LocalDate date);

}
