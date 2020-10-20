package com.pk.ms.services.month;

import com.pk.ms.dao.month.IMonthSummaryRepository;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.month.MonthSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class MonthSummaryService {

    private final IMonthSummaryRepository monthSummaryRepo;

    private final MonthService monthService;

    public MonthSummaryService(IMonthSummaryRepository monthSummaryRepo, MonthService monthService) {
        this.monthSummaryRepo = monthSummaryRepo;
        this.monthService = monthService;
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

    public MonthSummary createMonthSummary(@PathVariable("month_id") long monthId) {
        Month month = monthService.getMonthById(monthId);
        return saveMonthSummary(new MonthSummary(getFullfilledAmount(month), getFailedAmount(month), month));
    }

}
