package com.pk.ms.services.year;

import com.pk.ms.dao.year.IYearPlanRepository;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.year.YearPlan;
import org.springframework.stereotype.Service;

@Service
public class YearPlanService {

    private final IYearPlanRepository yearPlanRepo;

    private final YearService yearService;

    public YearPlanService(IYearPlanRepository yearPlanRepo, YearService yearService) {
        this.yearPlanRepo = yearPlanRepo;
        this.yearService = yearService;
    }

    public YearPlan saveYearPlan(YearPlan yearPlan) {
        return yearPlanRepo.save(yearPlan);
    }

    public YearPlan getYearPlanById(long id) {
        return yearPlanRepo.findById(id);
    }

    public void deleteYearPlan(long id) {
        yearPlanRepo.deleteById(id);
    }

    public YearPlan createYearPlan(long yearId, YearPlanInputDTO yearPlanInputDTO) {
        return saveYearPlan(new YearPlan(yearPlanInputDTO.getContent(), yearPlanInputDTO.getStartDate(),
                yearPlanInputDTO.getEndDate(), yearService.getYearById(yearId)));
    }

    public YearPlan updateYearPlan(long yearPlanId, YearPlanInputDTO yearPlanInputDTO) {

        YearPlan yearPlan = getYearPlanById(yearPlanId);

        if(yearPlanInputDTO.getContent() != null)
            yearPlan.setContent(yearPlanInputDTO.getContent());
        if(yearPlanInputDTO.getStartDate() != null)
            yearPlan.setStartDate(yearPlanInputDTO.getStartDate());
        if(yearPlanInputDTO.getEndDate() != null)
            yearPlan.setEndDate(yearPlanInputDTO.getEndDate());

        return saveYearPlan(yearPlan);
    }

    public void updateFulfilledStatus(long yearPlanId) {

        YearPlan yearPlan = getYearPlanById(yearPlanId);
        boolean fullfilled = yearPlan.isFulfilled();
        if(!fullfilled)
            fullfilled = true;
        else
            fullfilled = false;
        yearPlan.setFulfilled(fullfilled);
        saveYearPlan(yearPlan);
    }

}
