package com.pk.ms.services.day;

import com.pk.ms.abstracts.SummaryAccessAuthorizationService;
import com.pk.ms.dao.day.DaySummaryRepository;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryService implements SummaryAccessAuthorizationService {

    private final DaySummaryRepository repository;

    private final DayService dayService;

    private final ScheduleService scheduleService;

    public DaySummaryService(DaySummaryRepository repository, DayService dayService, ScheduleService scheduleService) {
        this.repository = repository;
        this.dayService = dayService;
        this.scheduleService = scheduleService;
    }

    public DaySummary getDaySummaryById(long scheduleId, long daySummaryId) {
        return getAuthenticatedNotNullDaySummaryById(scheduleId, daySummaryId);
    }

    public DaySummary getDaySummaryByScheduleIdAndDayId(long scheduleId, long dayId) {
        return getAuthenticatedNotNullDaySummaryByScheduleIdAndDayId(scheduleId, dayId);
    }

    public DaySummary createDaySummary(long scheduleId, long dayId) {
        Day day = dayService.getDayById(dayId);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        return save(new DaySummary(schedule, day));
    }

    public DaySummary updateDaySummary(long scheduleId, long daySummaryId) {
        DaySummary daySummary = getAuthenticatedNotNullDaySummaryById(scheduleId, daySummaryId);
        daySummary.countSummary();
        return save(daySummary);
    }

    private DaySummary getAuthenticatedNotNullDaySummaryById(long scheduleId, long daySummaryId) {
        DaySummary daySummary = repository.findById(daySummaryId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, daySummary));
        return daySummary;
    }

    private DaySummary getAuthenticatedNotNullDaySummaryByScheduleIdAndDayId(long scheduleId, long dayId) {
        return repository.findByScheduleIdAndDayId(scheduleId, dayId)
                .orElseThrow(ResourceNotAvailableException::new);
    }

    private DaySummary save(DaySummary daySummary) {
        return repository.save(daySummary);
    }
}
