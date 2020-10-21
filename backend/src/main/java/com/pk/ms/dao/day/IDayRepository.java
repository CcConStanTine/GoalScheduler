package com.pk.ms.dao.day;

import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IDayRepository extends CrudRepository<Day, Long> {

    public Day findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE week_id = ?")
    public List<Day> getDaysByWeekId(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE month_id = ?")
    public List<Day> getDaysByMonthId(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE month_id = ? AND day_date = ?")
    public Day findByMonthIdAndDayDate(long monthId, Date dayDate);

    @Query(nativeQuery = true, value = "SELECT * FROM day WHERE week_id = ? AND day_name = ?")
    public Day findByWeekIdAndDayName(long weekId, int dayName);

}
