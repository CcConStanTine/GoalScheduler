package com.pk.ms.services.week;

import com.pk.ms.dao.week.WeekPlanRepository;
import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeekPlanService {

    private final WeekPlanRepository weekPlanRepo;

    private final WeekService weekService;

    private final ScheduleService scheduleService;

    public WeekPlanService(WeekPlanRepository weekPlanRepo, WeekService weekService, ScheduleService scheduleService) {
        this.weekPlanRepo = weekPlanRepo;
        this.weekService = weekService;
        this.scheduleService = scheduleService;
    }

    public List<WeekPlan> getWeekPlansByScheduleIdAndWeekId(long scheduleId, long weekId) {
        return getWeekPlansByScheduleIdAndWeekIdFromRepo(scheduleId, weekId);
    }

    public WeekPlan createWeekPlan(long scheduleId, long weekId, WeekPlanInputDTO weekPlanInputDTO) {
        return saveWeekPlan(new WeekPlan(weekPlanInputDTO.getContent(),
                weekPlanInputDTO.getStartDate(),
                weekPlanInputDTO.getEndDate(),
                weekService.getWeekById(weekId),
                scheduleService.getScheduleById(scheduleId)));
    }

    public WeekPlan updateWeekPlan(long scheduleId, long weekPlanId, WeekPlanInputDTO weekPlanInputDTO) {

        WeekPlan weekPlan = getNotNullWeekPlanById(weekPlanId);

        if(hasAccess(scheduleId, weekPlan)) {

            String content = weekPlanInputDTO.getContent();
            if (isObjectNull(content))
                weekPlan.setContent(content);

            LocalDate startDate = weekPlanInputDTO.getStartDate();
            if (isObjectNull(startDate))
                weekPlan.setStartDate(startDate);

            LocalDate endDate = weekPlanInputDTO.getEndDate();
            if (isObjectNull(endDate))
                weekPlan.setEndDate(endDate);

            return saveWeekPlan(weekPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public String deleteWeekPlan(long scheduleId, long weekPlanId) {
        WeekPlan weekPlan = getNotNullWeekPlanById(weekPlanId);
        if(hasAccess(scheduleId, weekPlan)) {
            deleteWeekPlan(weekPlan);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public WeekPlan updateFulfilledStatus(long scheduleId, long weekPlanId) {
        WeekPlan weekPlan = getNotNullWeekPlanById(weekPlanId);
        if(hasAccess(scheduleId, weekPlan)) {
            boolean fulfilled = weekPlan.isFulfilled();
            fulfilled = !fulfilled;
            weekPlan.setFulfilled(fulfilled);
            return saveWeekPlan(weekPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }


    private List<WeekPlan> getWeekPlansByScheduleIdAndWeekIdFromRepo(long scheduleId, long weekId) {
        return weekPlanRepo.findWeekPlansByScheduleIdAndWeekId(scheduleId, weekId);
    }

    private WeekPlan getNotNullWeekPlanById(long id) {
        WeekPlan weekPlan = getWeekPlanByIdFromRepo(id);
        if(isObjectNull(weekPlan))
            throw new ResourceNotAvailableException();
        return weekPlan;
    }

    private WeekPlan getWeekPlanByIdFromRepo(long id) {
        return weekPlanRepo.findById(id);
    }

    private WeekPlan saveWeekPlan(WeekPlan weekPlan) {
        return weekPlanRepo.save(weekPlan);
    }

    private void deleteWeekPlan(WeekPlan weekPlan) {
        weekPlanRepo.delete(weekPlan);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, WeekPlan weekPlan) {
        return weekPlan.getSchedule().getScheduleId() == scheduleId;
    }
}
