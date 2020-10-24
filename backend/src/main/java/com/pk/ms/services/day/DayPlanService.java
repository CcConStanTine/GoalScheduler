package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDayPlanRepository;
import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.exceptions.AccessDeniedException;
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

    public String deleteDayPlan(long scheduleId, long dayId) {
        if(hasAccess(scheduleId, getDayPlanById(dayId))) {
            dayPlanRepo.deleteById(dayId);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public DayPlan createDayPlan(long scheduleId, long dayId, DayPlanInputDTO dayPlanInputDTO) {
        Day day = dayService.getDayById(dayId);
        if(dayService.hasAccess(scheduleId, day))
            return saveDayPlan(new DayPlan(dayPlanInputDTO.getContent(), dayPlanInputDTO.getStartDate(),
                dayPlanInputDTO.getEndDate(), day));
        else
            throw new AccessDeniedException("This user cannot create plan in this Day. ");
    }

    public DayPlan updateDayPlan(long scheduleId, long dayPlanId, DayPlanInputDTO dayPlanInputDTO) {
        DayPlan dayPlan = getDayPlanById(dayPlanId);
        if(hasAccess(scheduleId, dayPlan)) {
            if (dayPlanInputDTO.getContent() != null)
                dayPlan.setContent(dayPlanInputDTO.getContent());
            if (dayPlanInputDTO.getStartDate() != null)
                dayPlan.setStartDate(dayPlanInputDTO.getStartDate());
            if (dayPlanInputDTO.getEndDate() != null)
                dayPlan.setEndDate(dayPlanInputDTO.getEndDate());

            return saveDayPlan(dayPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public DayPlan updateFulfilledStatus(long scheduleId, long dayPlanId) {
        DayPlan dayPlan = getDayPlanById(dayPlanId);
        if(hasAccess(scheduleId, dayPlan)) {
            boolean fullfilled = dayPlan.isFulfilled();
            if (!fullfilled)
                fullfilled = true;
            else
                fullfilled = false;
            dayPlan.setFulfilled(fullfilled);
            return saveDayPlan(dayPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public boolean hasAccess(long scheduleId, DayPlan dayPlan) {
        return dayPlan.getDay().getWeek().getYear().getSchedule().getScheduleId() == scheduleId;
    }

}
