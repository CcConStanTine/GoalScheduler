package com.pk.ms.services.month;

import com.pk.ms.abstracts.PlanAccessAuthorizationService;
import com.pk.ms.dao.month.MonthPlanRepository;
import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthPlanService implements PlanAccessAuthorizationService {

    private final MonthPlanRepository repository;

    private final MonthService monthService;

    private final ScheduleService scheduleService;

    public MonthPlanService(MonthPlanRepository repository, MonthService monthService, ScheduleService scheduleService) {
        this.repository = repository;
        this.monthService = monthService;
        this.scheduleService = scheduleService;
    }

    public List<MonthPlan> getMonthPlansByScheduleIdAndMonthId(long scheduleId, long monthId) {
        return repository.findMonthPlansByScheduleIdAndMonthId(scheduleId, monthId);
    }

    public MonthPlan getMonthPlan(long scheduleId, long monthPlanId) {
        return getAuthorizedNotNullMonthPlanById(scheduleId, monthPlanId);
    }

    public MonthPlan createMonthPlan(long scheduleId, long monthId, MonthPlanInputDTO monthPlanInputDTO) {
        Month month = monthService.getMonthById(monthId);
        dataValidationForDataTypes(monthPlanInputDTO, month);
        MonthPlan monthPlan = new MonthPlan(monthPlanInputDTO.getContent(), monthPlanInputDTO.getStartDate(),
                monthPlanInputDTO.getEndDate(), scheduleService.getScheduleById(scheduleId), month);
        updateImportance(monthPlanInputDTO, monthPlan);
        updateUrgency(monthPlanInputDTO, monthPlan);
        return save(monthPlan);
    }

    public MonthPlan updateMonthPlan(long scheduleId, long monthPlanId, MonthPlanInputDTO monthPlanInputDTO) {
        MonthPlan monthPlan = getAuthorizedNotNullMonthPlanById(scheduleId, monthPlanId);
        Month month = monthPlan.getMonth();
        dataValidationForDataTypes(monthPlanInputDTO, month);
        updateMonthPlanAttributes(monthPlanInputDTO, monthPlan);
        return save(monthPlan);
    }

    private void dataValidationForDataTypes(MonthPlanInputDTO monthPlanInputDTO, Month month) {
        int monthNumber = month.getMonthName().getMonthNumber();
        if (monthPlanInputDTO.getStartDate().getMonthValue() != monthNumber)
            throw new IllegalArgumentException("Start date cannot include other month. ");
        if (monthPlanInputDTO.getEndDate().getMonthValue() != monthNumber)
            throw new IllegalArgumentException("End date cannot include other month. ");
    }

    public String deleteMonthPlan(long scheduleId, long monthPlanId) {
        delete(getAuthorizedNotNullMonthPlanById(scheduleId, monthPlanId));
        return "Plan deleted successfully. ";
    }

    public MonthPlan updateFulfilledStatus(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = getAuthorizedNotNullMonthPlanById(scheduleId, monthPlanId);
        monthPlan.setFulfilled(!monthPlan.isFulfilled());
        return save(monthPlan);
    }


    private MonthPlan getAuthorizedNotNullMonthPlanById(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = repository.findById(monthPlanId).orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, monthPlan));
        return monthPlan;
    }

    private MonthPlan save(MonthPlan monthPlan) {
        return repository.save(monthPlan);
    }

    private void updateMonthPlanAttributes(MonthPlanInputDTO monthPlanInputDTO, MonthPlan monthPlan) {
        monthPlan.setContent(monthPlanInputDTO.getContent());
        monthPlan.setStartDate(monthPlanInputDTO.getStartDate());
        monthPlan.setEndDate(monthPlanInputDTO.getEndDate());
        updateImportance(monthPlanInputDTO, monthPlan);
        updateUrgency(monthPlanInputDTO, monthPlan);
    }

    private void updateImportance(MonthPlanInputDTO monthPlanInputDTO, MonthPlan monthPlan) {
        if(monthPlanInputDTO.getImportance() != null)
            monthPlan.setImportance(monthPlanInputDTO.getImportance());
    }

    private void updateUrgency(MonthPlanInputDTO monthPlanInputDTO, MonthPlan monthPlan) {
        if(monthPlanInputDTO.getUrgency() != null)
            monthPlan.setUrgency(monthPlanInputDTO.getUrgency());
    }

    private void delete(MonthPlan monthPlan) {
        repository.delete(monthPlan);
    }
}
