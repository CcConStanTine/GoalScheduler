package com.pk.ms.dao.year;

import com.pk.ms.entities.year.Year;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearRepository extends CrudRepository<Year, Long> {

    List<Year> findAll();

    Year findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM year WHERE year_number = ?")
    Year findByYearNumber(int yearNumber);

}
