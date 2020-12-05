package com.pk.ms.dao.schedule;

import com.pk.ms.entities.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    Optional<Schedule> findById(long id);

}
