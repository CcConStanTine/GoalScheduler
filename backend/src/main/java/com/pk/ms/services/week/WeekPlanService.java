package com.pk.ms.services.week;

import com.pk.ms.abstracts.PlanAccessAuthorizationService;
import com.pk.ms.dao.week.WeekPlanRepository;
import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeekPlanService implements PlanAccessAuthorizationService {

    private final WeekPlanRepository repository;

    private final WeekService weekService;

    private final ScheduleService scheduleService;

    public WeekPlanService(WeekPlanRepository repository, WeekService weekService, ScheduleService scheduleService) {
        this.repository = repository;
        this.weekService = weekService;
        this.scheduleService = scheduleService;
    }

    public List<WeekPlan> getWeekPlansByScheduleIdAndWeekId(long scheduleId, long weekId) {
        return repository.findWeekPlansByScheduleIdAndWeekId(scheduleId, weekId);
    }

    public WeekPlan getWeekPlan(long scheduleId, long weekPlanId) {
        return getAuthorizedNotNullWeekPlanById(scheduleId, weekPlanId);
    }

    public WeekPlan createWeekPlan(long scheduleId, long weekId, WeekPlanInputDTO weekPlanInputDTO) {
        Week week = weekService.getWeekById(weekId);
        dataValidationForDataTypes(weekPlanInputDTO, week);
        WeekPlan weekPlan = new WeekPlan(weekPlanInputDTO.getContent(), weekPlanInputDTO.getStartDate(),
                weekPlanInputDTO.getEndDate(), scheduleService.getScheduleById(scheduleId), week);
        updateImportance(weekPlanInputDTO, weekPlan);
        updateUrgency(weekPlanInputDTO, weekPlan);
        return weekPlan;
    }

    public WeekPlan updateWeekPlan(long scheduleId, long weekPlanId, WeekPlanInputDTO weekPlanInputDTO) {
        WeekPlan weekPlan = getAuthorizedNotNullWeekPlanById(scheduleId, weekPlanId);
        Week week = weekPlan.getWeek();
        dataValidationForDataTypes(weekPlanInputDTO, week);
        updateWeekPlanAttributes(weekPlanInputDTO, weekPlan);
        return save(weekPlan);
    }

    private void dataValidationForDataTypes(WeekPlanInputDTO weekPlanInputDTO, Week week) {
        LocalDate startDate = week.getStartDate();
        LocalDate endDate = week.getEndDate();
        LocalDate inputStartDate = weekPlanInputDTO.getStartDate();
        LocalDate inputEndDate = weekPlanInputDTO.getEndDate();
        if(     !((inputStartDate.isAfter(startDate) && (inputStartDate.isBefore(endDate))) ||
                ((inputStartDate.isEqual(startDate) || inputStartDate.isEqual(endDate)))))
            throw new IllegalArgumentException("Start date cannot include other weeks. ");
        if(     !((inputEndDate.isAfter(startDate) && (inputEndDate.isBefore(endDate))) ||
                ((inputEndDate.isEqual(startDate) || inputEndDate.isEqual(endDate)))))
            throw new IllegalArgumentException("End date cannot include other weeks. ");
    }

    public String deleteWeekPlan(long scheduleId, long weekPlanId) {
        delete(getAuthorizedNotNullWeekPlanById(scheduleId, weekPlanId));
        return "Plan deleted successfully. ";
    }

    public WeekPlan updateFulfilledStatus(long scheduleId, long weekPlanId) {
        WeekPlan weekPlan = getAuthorizedNotNullWeekPlanById(scheduleId, weekPlanId);
        weekPlan.setFulfilled(!weekPlan.isFulfilled());
        return save(weekPlan);
    }

    private WeekPlan getAuthorizedNotNullWeekPlanById(long scheduleId, long weekPlanId) {
        WeekPlan weekPlan = repository.findById(weekPlanId).orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, weekPlan));
        return weekPlan;
    }

    private WeekPlan save(WeekPlan weekPlan) {
        return repository.save(weekPlan);
    }

    private void updateWeekPlanAttributes(WeekPlanInputDTO weekPlanInputDTO, WeekPlan weekPlan) {
        weekPlan.setContent(weekPlanInputDTO.getContent());
        weekPlan.setStartDate(weekPlanInputDTO.getStartDate());
        weekPlan.setEndDate(weekPlanInputDTO.getEndDate());
        updateImportance(weekPlanInputDTO, weekPlan);
        updateUrgency(weekPlanInputDTO, weekPlan);
    }

    private void updateImportance(WeekPlanInputDTO weekPlanInputDTO, WeekPlan weekPlan) {
        if(weekPlanInputDTO.getImportance() != null)
            weekPlan.setImportance(weekPlanInputDTO.getImportance());
    }

    private void updateUrgency(WeekPlanInputDTO weekPlanInputDTO, WeekPlan weekPlan) {
        if(weekPlanInputDTO.getUrgency() != null)
            weekPlan.setUrgency(weekPlanInputDTO.getUrgency());
    }

    private void delete(WeekPlan weekPlan) {
        repository.delete(weekPlan);
    }
}
