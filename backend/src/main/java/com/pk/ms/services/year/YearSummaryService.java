package com.pk.ms.services.year;

import com.pk.ms.dao.year.IYearSummaryRepository;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.entities.year.YearSummary;
import com.pk.ms.exceptions.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class YearSummaryService {

    private final IYearSummaryRepository yearSummaryRepo;

    private final YearService yearService;

    public YearSummaryService(IYearSummaryRepository yearSummaryRepo, YearService yearService) {
        this.yearSummaryRepo = yearSummaryRepo;
        this.yearService = yearService;
    }

    public YearSummary getYearSummary(long scheduleId, long yearSummaryId) {
        YearSummary yearSummary = getYearSummaryById(yearSummaryId);
        if(hasAccess(scheduleId, yearSummary))
            return yearSummaryRepo.findById(yearSummaryId);
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public YearSummary getYearSummaryById(long yearSummaryId) {
        return yearSummaryRepo.findById(yearSummaryId);
    }

    public YearSummary saveYearSummary(YearSummary yearSummary) {
        return yearSummaryRepo.save(yearSummary);
    }

    public int getFullfilledAmount(Year year) {
        int fulfilled=0;
        for (YearPlan yearPlan: year.getYearPlansList()) {
            if(yearPlan.isFulfilled())
                fulfilled++;
        }
        return fulfilled;
    }

    public int getFailedAmount(Year year) {
        return year.getYearPlansList().size() - getFullfilledAmount(year);
    }

    public YearSummary createYearSummary(long scheduleId, long yearId) {
        Year year = yearService.getYearById(yearId);
        if(yearService.hasAccess(scheduleId, year))
            return saveYearSummary(new YearSummary(getFullfilledAmount(year), getFailedAmount(year), year));
        else
            throw new AccessDeniedException("This user cannot create summary in this Year. ");
    }

    private boolean hasAccess(long scheduleId, YearSummary yearSummary) {
        return yearSummary.getYear().getSchedule().getScheduleId() == scheduleId;
    }

}
