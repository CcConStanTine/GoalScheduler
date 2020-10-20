package com.pk.ms.dao.schedule;

import com.pk.ms.entities.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScheduleRepository extends CrudRepository<Schedule, Long> {

    public Schedule findById(long id);

}
