package com.pk.ms.dao.year;

import com.pk.ms.entities.year.Year;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IYearRepository extends CrudRepository<Year, Long> {

    public Year findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM year WHERE schedule_id = ?")
    public List<Year> findAllByScheduleId(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM year WHERE schedule_id = ? AND year_number = ?")
    public Year findByScheduleIdAndYearNumber(long scheduleId, int yearNumber);


}
