package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDaySummaryRepository;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.exceptions.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryService {


    private final IDaySummaryRepository daySummaryRepo;

    private final DayService dayService;

    public DaySummaryService(IDaySummaryRepository daySummaryRepo, DayService dayService) {
        this.daySummaryRepo = daySummaryRepo;
        this.dayService = dayService;
    }

    public DaySummary getDaySummary(long scheduleId, long daySummaryId) {
        DaySummary daySummary = getDaySummaryById(daySummaryId);
        if(hasAccess(scheduleId, daySummary))
            return daySummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public DaySummary getDaySummaryById(long daySummaryId) {
        return daySummaryRepo.findById(daySummaryId);
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

    public DaySummary createDaySummary(long scheduleId, long dayId) {
        Day day = dayService.getDayById(dayId);
        if(dayService.hasAccess(scheduleId,day))
            return saveDay(new DaySummary(getFullfilledAmount(day), getFailedAmount(day), day));
        else
            throw new AccessDeniedException("This user cannot create summary in this Day. ");
    }

    private boolean hasAccess(long scheduleId, DaySummary daySummary) {
        return daySummary.getDay().getWeek().getYear().getSchedule().getScheduleId() == scheduleId;
    }

}
