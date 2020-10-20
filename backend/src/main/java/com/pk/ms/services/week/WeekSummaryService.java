package com.pk.ms.services.week;

import com.pk.ms.dao.week.IWeekSummaryRepository;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.entities.week.WeekSummary;
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

    public WeekSummary createWeekSummary(long id) {
        Week week = weekService.getWeekById(id);
        return saveWeekSummary(new WeekSummary(getFullfilledAmount(week), getFailedAmount(week), week));
    }

}
