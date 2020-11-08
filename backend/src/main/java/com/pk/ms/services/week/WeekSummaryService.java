package com.pk.ms.services.week;

import com.pk.ms.dao.week.WeekSummaryRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class WeekSummaryService {

    private final WeekSummaryRepository weekSummaryRepo;

    private final WeekService weekService;

    private final ScheduleService scheduleService;

    public WeekSummaryService(WeekSummaryRepository weekSummaryRepo, WeekService weekService, ScheduleService scheduleService) {
        this.weekSummaryRepo = weekSummaryRepo;
        this.weekService = weekService;
        this.scheduleService = scheduleService;
    }

    public WeekSummary getWeekSummaryById(long scheduleId, long weekSummaryId) {
        WeekSummary weekSummary = getNotNullWeekSummaryById(weekSummaryId);
        if(hasAccess(scheduleId, weekSummary))
            return weekSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public WeekSummary getWeekSummaryByScheduleIdAndWeekId(long scheduleId, long weekId) {
        WeekSummary weekSummary = getNotNullWeekSummaryByScheduleIdAndWeekId(scheduleId, weekId);
        if(hasAccess(scheduleId, weekSummary))
            return weekSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public WeekSummary createWeekSummary(long scheduleId, long weekId) {
        WeekSummary weekSummary = getWeekSummaryByScheduleIdAndWeekIdFromRepo(scheduleId, weekId);
        if(isObjectNull(weekSummary)) {
            Week week = weekService.getWeekById(weekId);
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            return saveWeekSummary(new WeekSummary(week, schedule));
        }
        else {
            updateWeekSummary(scheduleId, weekSummary);
            return saveWeekSummary(weekSummary);
        }
    }


    private WeekSummary getNotNullWeekSummaryById(long weekSummaryId) {
        WeekSummary weekSummary = getWeekSummaryByIdFromRepo(weekSummaryId);
        if(isObjectNull(weekSummary))
            throw new ResourceNotAvailableException();
        return weekSummary;
    }

    private WeekSummary getWeekSummaryByIdFromRepo(long weekSummaryId) {
        return weekSummaryRepo.findById(weekSummaryId);
    }

    private WeekSummary getNotNullWeekSummaryByScheduleIdAndWeekId(long scheduleId, long weekId) {
        WeekSummary weekSummary = getWeekSummaryByScheduleIdAndWeekIdFromRepo(scheduleId, weekId);
        if(isObjectNull(weekSummary))
            throw new ResourceNotAvailableException();
        return weekSummary;
    }

    private WeekSummary getWeekSummaryByScheduleIdAndWeekIdFromRepo(long scheduleId, long weekId) {
        return weekSummaryRepo.findByScheduleIdAndWeekId(scheduleId, weekId);
    }

    private WeekSummary saveWeekSummary(WeekSummary weekSummary) {
        return weekSummaryRepo.save(weekSummary);
    }

    private void updateWeekSummary(long scheduleId, WeekSummary weekSummary) {
        if (hasAccess(scheduleId, weekSummary)) {
            weekSummary.countFulfilledAmount();
            weekSummary.countFailedAmount();
        }
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, WeekSummary weekSummary) {
        return weekSummary.getSchedule().getScheduleId() == scheduleId;
    }
}
