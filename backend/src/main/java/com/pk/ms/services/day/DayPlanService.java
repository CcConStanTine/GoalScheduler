package com.pk.ms.services.day;

import com.pk.ms.abstracts.PlanAccessAuthorizationService;
import com.pk.ms.dao.day.DayPlanRepository;
import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayPlanService implements PlanAccessAuthorizationService {

    private final DayPlanRepository repository;

    private final DayService dayService;

    private final ScheduleService scheduleService;

    public DayPlanService(DayPlanRepository repository, DayService dayService, ScheduleService scheduleService) {
        this.repository = repository;
        this.dayService = dayService;
        this.scheduleService = scheduleService;
    }

    public List<DayPlan> getDayPlansByScheduleIdAndDayId(long scheduleId, long dayId) {
        return repository.findDayPlansByScheduleIdAndDayId(scheduleId, dayId);
    }

    public DayPlan getDayPlan(long scheduleId, long dayPlanId) {
        return getAuthorizedNotNullDayPlanById(scheduleId, dayPlanId);
    }

    public DayPlan createDayPlan(long scheduleId, long dayId, DayPlanInputDTO dayPlanInputDTO) {
        DayPlan dayPlan = new DayPlan(dayPlanInputDTO.getContent(), dayPlanInputDTO.getStartDate(),
                dayPlanInputDTO.getEndDate(), scheduleService.getScheduleById(scheduleId),
                dayService.getDayById(dayId));
        updateImportance(dayPlanInputDTO, dayPlan);
        updateUrgency(dayPlanInputDTO, dayPlan);
        return dayPlan;
    }

    public DayPlan updateDayPlan(long scheduleId, long dayPlanId, DayPlanInputDTO dayPlanInputDTO) {
        DayPlan dayPlan = getAuthorizedNotNullDayPlanById(scheduleId, dayPlanId);
        updateDayPlanAttributes(dayPlanInputDTO, dayPlan);
        return save(dayPlan);
    }

    public String deleteDayPlan(long scheduleId, long dayPlanId) {
        delete(getAuthorizedNotNullDayPlanById(scheduleId, dayPlanId));
        return "Plan deleted successfully. ";
    }

    public DayPlan updateFulfilledStatus(long scheduleId, long dayPlanId) {
        DayPlan dayPlan = getAuthorizedNotNullDayPlanById(scheduleId, dayPlanId);
        dayPlan.setFulfilled(!dayPlan.isFulfilled());
        return save(dayPlan);
    }

    private DayPlan getAuthorizedNotNullDayPlanById(long scheduleId, long dayPlanId) {
        DayPlan dayPlan = repository.findById(dayPlanId).orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, dayPlan));
        return dayPlan;
    }

    private DayPlan save(DayPlan dayPlan) {
        return repository.save(dayPlan);
    }

    private void updateDayPlanAttributes(DayPlanInputDTO dayPlanInputDTO, DayPlan dayPlan) {
        dayPlan.setContent(dayPlanInputDTO.getContent());
        dayPlan.setStartDate(dayPlanInputDTO.getStartDate());
        dayPlan.setEndDate(dayPlanInputDTO.getEndDate());
        updateImportance(dayPlanInputDTO, dayPlan);
        updateUrgency(dayPlanInputDTO, dayPlan);
    }

    private void updateImportance(DayPlanInputDTO dayPlanInputDTO, DayPlan dayPlan) {
        if(dayPlanInputDTO.getImportance() != null)
            dayPlan.setImportance(dayPlanInputDTO.getImportance());
    }

    private void updateUrgency(DayPlanInputDTO dayPlanInputDTO, DayPlan dayPlan) {
        if(dayPlanInputDTO.getUrgency() != null)
            dayPlan.setUrgency(dayPlanInputDTO.getUrgency());
    }

    private void delete(DayPlan dayPlan) {
        repository.delete(dayPlan);
    }
}
