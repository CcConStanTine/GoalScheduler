package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.IScheduleRepository;
import com.pk.ms.entities.schedule.Schedule;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final IScheduleRepository scheduleRepo;

    public ScheduleService(IScheduleRepository scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public Schedule save(Schedule schedule) { return scheduleRepo.save(schedule); }

    public Schedule getScheduleById(long id) { return scheduleRepo.findById(id); }

}
