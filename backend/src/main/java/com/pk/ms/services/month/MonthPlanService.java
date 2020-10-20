package com.pk.ms.services.month;

import com.pk.ms.dao.month.IMonthPlanRepository;
import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.month.MonthPlan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class MonthPlanService {

    private final IMonthPlanRepository monthPlanRepo;

    private final MonthService monthService;

    public MonthPlanService(IMonthPlanRepository monthPlanRepo, MonthService monthService) {
        this.monthPlanRepo = monthPlanRepo;
        this.monthService = monthService;
    }

    public MonthPlan saveMonthPlan(MonthPlan monthPlan) { return monthPlanRepo.save(monthPlan); }

    public MonthPlan getMonthPlanById(long id) { return monthPlanRepo.findById(id); }

    public void deleteMonthPlan(long id) { monthPlanRepo.deleteById(id); }

    public MonthPlan createYearPlan(long monthId, MonthPlanInputDTO monthPlanInputDTO) {
        return saveMonthPlan(new MonthPlan(monthPlanInputDTO.getContent(), monthPlanInputDTO.getStartDate(),
                monthPlanInputDTO.getEndDate(), monthService.getMonthById(monthId)));
    }

    public MonthPlan updateMonthPlan(long monthPlanId, MonthPlanInputDTO monthPlanInputDTO) {

        MonthPlan monthPlan = getMonthPlanById(monthPlanId);

        if(monthPlanInputDTO.getContent() != null)
            monthPlan.setContent(monthPlanInputDTO.getContent());
        if(monthPlanInputDTO.getStartDate() != null)
            monthPlan.setStartDate(monthPlanInputDTO.getStartDate());
        if(monthPlanInputDTO.getEndDate() != null)
            monthPlan.setEndDate(monthPlanInputDTO.getEndDate());

        return saveMonthPlan(monthPlan);
    }

    public void updateFulfilledStatus(@PathVariable("month_plan_id") long monthPlanId) {

        MonthPlan monthPlan = getMonthPlanById(monthPlanId);
        boolean fullfilled = monthPlan.isFulfilled();
        if(!fullfilled)
            fullfilled = true;
        else
            fullfilled = false;
        monthPlan.setFulfilled(fullfilled);
        saveMonthPlan(monthPlan);
    }

}
