package com.pk.ms.services.week;

import com.pk.ms.dao.week.IWeekSummaryRepository;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class WeekSummaryService {


    private final IWeekSummaryRepository weekSummaryRepo;

    private final WeekService weekService;

    public WeekSummaryService(IWeekSummaryRepository weekSummaryRepo, WeekService weekService) {
        this.weekSummaryRepo = weekSummaryRepo;
        this.weekService = weekService;
    }

    public WeekSummary getWeekSummaryById(long id) {
        return weekSummaryRepo.findById(id);
    }

    public WeekSummary getWeekSummary(long scheduleId, long weekSummaryId) {
        WeekSummary weekSummary = getWeekSummaryById(weekSummaryId);
        if(weekSummary == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, weekSummary))
            return weekSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public WeekSummary saveWeekSummary(WeekSummary weekSummary) {
        return weekSummaryRepo.save(weekSummary);
    }

    public int getFullfilledAmount(Week week) {
        int fulfilled=0;
        for (WeekPlan weekPlan: week.getWeekPlansList()) {
            if(weekPlan.isFulfilled())
                fulfilled++;
        }
        return fulfilled;
    }

    public int getFailedAmount(Week week) {
        return week.getWeekPlansList().size() - getFullfilledAmount(week);
    }

    public WeekSummary createWeekSummary(long scheduleId, long weekId) {
        Week week = weekService.getWeekById(weekId);
        if(week == null)
            throw new ResourceNotAvailableException();
        if(weekService.hasAccess(scheduleId, week))
            return saveWeekSummary(new WeekSummary(getFullfilledAmount(week), getFailedAmount(week), week));
        else
            throw new AccessDeniedException("This user cannot create summary in this Week. ");

    }

    private boolean hasAccess(long scheduleId, WeekSummary weekSummary) {
        return weekSummary.getWeek().getYear().getSchedule().getScheduleId() == scheduleId;
    }

}
