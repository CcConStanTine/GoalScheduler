package com.pk.ms.services.year;

import com.pk.ms.dao.year.IYearSummaryRepository;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.entities.year.YearSummary;
import org.springframework.stereotype.Service;

@Service
public class YearSummaryService {

    private final IYearSummaryRepository yearSummaryRepo;

    private final YearService yearService;

    public YearSummaryService(IYearSummaryRepository yearSummaryRepo, YearService yearService) {
        this.yearSummaryRepo = yearSummaryRepo;
        this.yearService = yearService;
    }

    public YearSummary getYearSummaryById(long id) {
        return yearSummaryRepo.findById(id);
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

    public YearSummary createYearSummary(long yearId) {
        Year year = yearService.getYearById(yearId);
        return saveYearSummary(new YearSummary(getFullfilledAmount(year), getFailedAmount(year), year));
    }


}
