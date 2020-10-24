package com.pk.ms.services.week;

import com.pk.ms.dao.week.IWeekPlanRepository;
import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.exceptions.AccessDeniedException;
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

    public String deleteWeekPlan(long scheduleId, long weekPlanId) {
        if(hasAccess(scheduleId, getWeekPlanById(weekPlanId))) {
            weekPlanRepo.deleteById(weekPlanId);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public WeekPlan createWeekPlan(long scheduleId, long weekId, WeekPlanInputDTO weekPlanInputDTO) {
        Week week = weekService.getWeekById(weekId);
        if(weekService.hasAccess(scheduleId, week))
            return saveWeekPlan(new WeekPlan(weekPlanInputDTO.getContent(), weekPlanInputDTO.getStartDate(),
                weekPlanInputDTO.getEndDate(), week));
        else
            throw new AccessDeniedException("This user cannot create plan in this Week. ");
    }

    public WeekPlan updateWeekPlan(long scheduleId, long weekId, WeekPlanInputDTO weekPlanInputDTO) {

        WeekPlan weekPlan = getWeekPlanById(weekId);
        if(hasAccess(scheduleId, weekPlan)) {
            if (weekPlanInputDTO.getContent() != null)
                weekPlan.setContent(weekPlanInputDTO.getContent());
            if (weekPlanInputDTO.getStartDate() != null)
                weekPlan.setStartDate(weekPlanInputDTO.getStartDate());
            if (weekPlanInputDTO.getEndDate() != null)
                weekPlan.setEndDate(weekPlanInputDTO.getEndDate());

            return saveWeekPlan(weekPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public WeekPlan updateFulfilledStatus(long scheduleId, long weekPlanId) {

        WeekPlan weekPlan = getWeekPlanById(weekPlanId);
        if(hasAccess(scheduleId, weekPlan)) {
            boolean fullfilled = weekPlan.isFulfilled();
            if (!fullfilled)
                fullfilled = true;
            else
                fullfilled = false;
            weekPlan.setFulfilled(fullfilled);
            return saveWeekPlan(weekPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public boolean hasAccess(long scheduleId, WeekPlan weekPlan) {
        return weekPlan.getWeek().getYear().getSchedule().getScheduleId() == scheduleId;
    }
}
