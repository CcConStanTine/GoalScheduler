package com.pk.ms.services.week;

import com.pk.ms.dao.week.IWeekPlanRepository;
import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.WeekPlan;
import org.springframework.stereotype.Service;

@Service
public class WeekPlanService {


    private final IWeekPlanRepository weekPlanRepo;

    private final WeekService weekService;

    public WeekPlanService(IWeekPlanRepository weekPlanRepo, WeekService weekService) {
        this.weekPlanRepo = weekPlanRepo;
        this.weekService = weekService;
    }

    public WeekPlan saveWeekPlan(WeekPlan weekPlan) {
        return weekPlanRepo.save(weekPlan);
    }

    public WeekPlan getWeekPlanById(long id) {
        return weekPlanRepo.findById(id);
    }

    public void deleteWeekPlan(long id) {
        weekPlanRepo.deleteById(id);
    }

    public WeekPlan createWeekPlan(long id, WeekPlanInputDTO weekPlanInputDTO) {
        return saveWeekPlan(new WeekPlan(weekPlanInputDTO.getContent(), weekPlanInputDTO.getStartDate(),
                weekPlanInputDTO.getEndDate(), weekService.getWeekById(id)));
    }

    public WeekPlan updateWeekPlan(long id, WeekPlanInputDTO weekPlanInputDTO) {

        WeekPlan weekPlan = getWeekPlanById(id);

        if(weekPlanInputDTO.getContent() != null)
            weekPlan.setContent(weekPlanInputDTO.getContent());
        if(weekPlanInputDTO.getStartDate() != null)
            weekPlan.setStartDate(weekPlanInputDTO.getStartDate());
        if(weekPlanInputDTO.getEndDate() != null)
            weekPlan.setEndDate(weekPlanInputDTO.getEndDate());

        return saveWeekPlan(weekPlan);
    }

    public void updateFulfilledStatus(long id) {

        WeekPlan weekPlan = getWeekPlanById(id);
        boolean fullfilled = weekPlan.isFulfilled();
        if(!fullfilled)
            fullfilled = true;
        else
            fullfilled = false;
        weekPlan.setFulfilled(fullfilled);
        saveWeekPlan(weekPlan);
    }

}
