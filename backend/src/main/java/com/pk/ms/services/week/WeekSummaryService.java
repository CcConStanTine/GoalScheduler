package com.pk.ms.services.week;

import com.pk.ms.abstracts.SummaryAccessAuthorizationService;
import com.pk.ms.dao.week.WeekSummaryRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class WeekSummaryService implements SummaryAccessAuthorizationService {

    private final WeekSummaryRepository repository;

    private final WeekService weekService;

    private final ScheduleService scheduleService;

    public WeekSummaryService(WeekSummaryRepository repository, WeekService weekService, ScheduleService scheduleService) {
        this.repository = repository;
        this.weekService = weekService;
        this.scheduleService = scheduleService;
    }

    public WeekSummary getWeekSummaryById(long scheduleId, long weekSummaryId) {
        return getAuthorizedNotNullWeekSummaryById(scheduleId, weekSummaryId);
    }

    public WeekSummary getWeekSummaryByScheduleIdAndWeekId(long scheduleId, long weekId) {
        return getAuthorizedNotNullWeekSummaryByScheduleIdAndWeekId(scheduleId, weekId);
    }

    public WeekSummary createWeekSummary(long scheduleId, long weekId) {
        Week week = weekService.getWeekById(weekId);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        return save(new WeekSummary(schedule, week));
    }

    public WeekSummary updateWeekSummary(long scheduleId, long weekId) {
        WeekSummary weekSummary = getAuthorizedNotNullWeekSummaryById(scheduleId, weekId);
        countWeekSummary(weekSummary);
        return save(weekSummary);
    }

    private WeekSummary getAuthorizedNotNullWeekSummaryById(long scheduleId, long weekSummaryId) {
        WeekSummary weekSummary = repository.findById(weekSummaryId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, weekSummary));
        return weekSummary;
    }

    private WeekSummary getAuthorizedNotNullWeekSummaryByScheduleIdAndWeekId(long scheduleId, long weekId) {
        WeekSummary weekSummary = repository.findByScheduleIdAndWeekId(scheduleId, weekId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, weekSummary));
        return weekSummary;
    }

    private WeekSummary save(WeekSummary weekSummary) {
        return repository.save(weekSummary);
    }

    private void countWeekSummary(WeekSummary weekSummary) {
        weekSummary.countFulfilledAmount();
        weekSummary.countFailedAmount();
    }
}
