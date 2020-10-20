package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDayPlanRepository;
import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.DayPlan;
import org.springframework.stereotype.Service;

@Service
public class DayPlanService {

    private final IDayPlanRepository dayPlanRepo;

    private final DayService dayService;

    public DayPlanService(IDayPlanRepository dayPlanRepo, DayService dayService) {
        this.dayPlanRepo = dayPlanRepo;
        this.dayService = dayService;
    }

    public DayPlan saveDayPlan(DayPlan dayPlan) {
        return dayPlanRepo.save(dayPlan);
    }

    public DayPlan getDayPlanById(long id) {
        return dayPlanRepo.findById(id);
    }

    public void deleteDayPlan(long id) {
        dayPlanRepo.deleteById(id);
    }

    public DayPlan createDayPlan(long id, DayPlanInputDTO dayPlanInputDTO) {
        return saveDayPlan(new DayPlan(dayPlanInputDTO.getContent(), dayPlanInputDTO.getStartDate(),
                dayPlanInputDTO.getEndDate(), dayService.getDayById(id)));
    }

    public DayPlan updateDayPlan(long id, DayPlanInputDTO dayPlanInputDTO) {
        DayPlan dayPlan = getDayPlanById(id);

        if(dayPlanInputDTO.getContent() != null)
            dayPlan.setContent(dayPlanInputDTO.getContent());
        if(dayPlanInputDTO.getStartDate() != null)
            dayPlan.setStartDate(dayPlanInputDTO.getStartDate());
        if(dayPlanInputDTO.getEndDate() != null)
            dayPlan.setEndDate(dayPlanInputDTO.getEndDate());

        return saveDayPlan(dayPlan);
    }

    public void updateFulfilledStatus(long dayPlanId) {
        DayPlan dayPlan = getDayPlanById(dayPlanId);
        boolean fullfilled = dayPlan.isFulfilled();
        if(!fullfilled)
            fullfilled = true;
        else
            fullfilled = false;
        dayPlan.setFulfilled(fullfilled);
        saveDayPlan(dayPlan);
    }

}
