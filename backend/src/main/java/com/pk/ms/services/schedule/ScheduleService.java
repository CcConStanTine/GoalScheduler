package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.ScheduleRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;

    public ScheduleService(ScheduleRepository scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public Schedule saveSchedule(Schedule schedule) { return scheduleRepo.save(schedule); }

    public Schedule getScheduleById(long id) {
        return getNotNullScheduleById(id);
    }

    private Schedule getNotNullScheduleById(long id) {
        return scheduleRepo.findById(id).orElseThrow(ResourceNotAvailableException::new);
    }
}
