package com.pk.ms.services.day;

import com.pk.ms.dao.day.DayPlanRepository;
import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DayPlanService {

    private final DayPlanRepository dayPlanRepo;

    private final DayService dayService;

    private final ScheduleService scheduleService;

    public DayPlanService(DayPlanRepository dayPlanRepo, DayService dayService, ScheduleService scheduleService) {
        this.dayPlanRepo = dayPlanRepo;
        this.dayService = dayService;
        this.scheduleService = scheduleService;
    }

    public List<DayPlan> getDayPlansByScheduleIdAndDayId(long scheduleId, long dayId) {
        return getDayPlansByScheduleIdAndDayIdFromRepo(scheduleId, dayId);
    }

    public DayPlan createDayPlan(long scheduleId, long dayId, DayPlanInputDTO dayPlanInputDTO) {
        return saveDayPlan(new DayPlan(dayPlanInputDTO.getContent(),
                dayPlanInputDTO.getStartDate(),
                dayPlanInputDTO.getEndDate(),
                dayService.getDayById(dayId),
                scheduleService.getScheduleById(scheduleId)));
    }

    public DayPlan updateDayPlan(long scheduleId, long dayPlanId, DayPlanInputDTO dayPlanInputDTO) {

        DayPlan dayPlan = getNotNullDayPlanById(dayPlanId);

        if(hasAccess(scheduleId, dayPlan)) {

            String content = dayPlanInputDTO.getContent();
            if (isObjectNull(content))
                dayPlan.setContent(content);

            LocalTime startDate = dayPlanInputDTO.getStartDate();
            if (isObjectNull(startDate))
                dayPlan.setStartDate(startDate);

            LocalTime endDate = dayPlanInputDTO.getEndDate();
            if (isObjectNull(endDate))
                dayPlan.setEndDate(endDate);

            return saveDayPlan(dayPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public String deleteDayPlan(long scheduleId, long dayPlanId) {
        DayPlan dayPlan = getNotNullDayPlanById(dayPlanId);
        if(hasAccess(scheduleId, dayPlan)) {
            deleteDayPlan(dayPlan);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public DayPlan updateFulfilledStatus(long scheduleId, long dayPlanId) {
        DayPlan dayPlan = getNotNullDayPlanById(dayPlanId);
        if(hasAccess(scheduleId, dayPlan)) {
            boolean fulfilled = dayPlan.isFulfilled();
            fulfilled = !fulfilled;
            dayPlan.setFulfilled(fulfilled);
            return saveDayPlan(dayPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }


    private List<DayPlan> getDayPlansByScheduleIdAndDayIdFromRepo(long scheduleId, long dayId) {
        return dayPlanRepo.findDayPlansByScheduleIdAndDayId(scheduleId, dayId);
    }

    private DayPlan getNotNullDayPlanById(long id) {
        DayPlan dayPlan = getDayPlanByIdFromRepo(id);
        if(isObjectNull(dayPlan))
            throw new ResourceNotAvailableException();
        return dayPlan;
    }

    private DayPlan getDayPlanByIdFromRepo(long id) {
        return dayPlanRepo.findById(id);
    }

    private DayPlan saveDayPlan(DayPlan dayPlan) {
        return dayPlanRepo.save(dayPlan);
    }

    private void deleteDayPlan(DayPlan dayPlan) {
        dayPlanRepo.delete(dayPlan);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, DayPlan dayPlan) {
        return dayPlan.getSchedule().getScheduleId() == scheduleId;
    }
}
