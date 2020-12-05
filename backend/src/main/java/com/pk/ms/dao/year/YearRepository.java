package com.pk.ms.dao.year;

import com.pk.ms.entities.year.Year;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearRepository extends CrudRepository<Year, Long> {

    List<Year> findAll();

    Optional<Year> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM year WHERE year_number = ?")
    Optional<Year> findByYearNumber(int yearNumber);

}
