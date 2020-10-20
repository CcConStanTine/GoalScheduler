package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDaySummaryRepository;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.entities.day.DaySummary;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryService {


    private final IDaySummaryRepository daySummaryRepo;

    private final DayService dayService;

    public DaySummaryService(IDaySummaryRepository daySummaryRepo, DayService dayService) {
        this.daySummaryRepo = daySummaryRepo;
        this.dayService = dayService;
    }

    public DaySummary getDaySummaryById(long id) {
        return daySummaryRepo.findById(id);
    }

    public DaySummary saveDay(DaySummary daySummary) {
        return daySummaryRepo.save(daySummary);
    }

    public int getFullfilledAmount(Day day) {
        int fulfilled=0;
        for (DayPlan dayPlan: day.getDayPlansList()) {
            if(dayPlan.isFulfilled())
                fulfilled++;
        }
        return fulfilled;
    }

    public int getFailedAmount(Day day) {
        return day.getDayPlansList().size() - getFullfilledAmount(day);
    }

    public DaySummary createDaySummary(long id) {
        Day day = dayService.getDayById(id);
        return saveDay(new DaySummary(getFullfilledAmount(day), getFailedAmount(day), day));
    }

}
