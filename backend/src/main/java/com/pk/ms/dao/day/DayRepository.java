package com.pk.ms.dao.day;

import com.pk.ms.entities.day.Day;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {

    Optional<Day> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE month_id = ?")
    List<Day> findAllByMonthId(long monthId);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE day_date = ?")
    Optional<Day> findByDate(LocalDate date);

}
