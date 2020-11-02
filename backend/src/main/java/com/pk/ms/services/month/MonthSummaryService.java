package com.pk.ms.services.month;

import com.pk.ms.dao.month.IMonthSummaryRepository;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class MonthSummaryService {

    private final IMonthSummaryRepository monthSummaryRepo;

    private final MonthService monthService;

    public MonthSummaryService(IMonthSummaryRepository monthSummaryRepo, MonthService monthService) {
        this.monthSummaryRepo = monthSummaryRepo;
        this.monthService = monthService;
    }

    public MonthSummary getMonthSummary(long scheduleId, long monthSummaryId) {
        MonthSummary monthSummary = getMonthSummaryById(monthSummaryId);
        if(monthSummary == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, monthSummary))
            return monthSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public MonthSummary getMonthSummaryById(long id) {
        return monthSummaryRepo.findById(id);
    }

    public MonthSummary saveMonthSummary(MonthSummary monthSummary) {
        return monthSummaryRepo.save(monthSummary);
    }

    public int getFullfilledAmount(Month month) {
        int fulfilled=0;
        for (MonthPlan monthPlan: month.getMonthPlansList()) {
            if(monthPlan.isFulfilled())
                fulfilled++;
        }
        return fulfilled;
    }

    public int getFailedAmount(Month month) {
        return month.getMonthPlansList().size() - getFullfilledAmount(month);
    }

    public MonthSummary createMonthSummary(long scheduleId, long monthId) {
        Month month = monthService.getMonthById(monthId);
        if(month == null)
            throw new ResourceNotAvailableException();
        if(monthService.hasAccess(scheduleId, month))
            return saveMonthSummary(new MonthSummary(getFullfilledAmount(month), getFailedAmount(month), month));
        else
            throw new AccessDeniedException("This user cannot create summary in this Month. ");
    }

    private boolean hasAccess(long scheduleId, MonthSummary monthSummary) {
        return monthSummary.getMonth().getYear().getSchedule().getScheduleId() == scheduleId;
    }

}
