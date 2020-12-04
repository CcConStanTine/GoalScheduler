package com.pk.ms.services.year;

import com.pk.ms.abstracts.PlanAccessAuthorizationService;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.year.YearPlanRepository;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearPlanService implements PlanAccessAuthorizationService {

    private final YearPlanRepository repository;

    private final YearService yearService;

    private final ScheduleService scheduleService;

    public YearPlanService(YearPlanRepository repository, YearService yearService, ScheduleService scheduleService) {
        this.repository = repository;
        this.yearService = yearService;
        this.scheduleService = scheduleService;
    }

    public List<YearPlan> getYearPlansByScheduleIdAndYearId(long scheduleId, long yearId) {
        return repository.findYearPlansByScheduleIdAndYearId(scheduleId, yearId);
    }

    public YearPlan getYearPlan(long scheduleId, long yearPlanId) {
        return getAuthorizedNotNullYearPlanById(scheduleId, yearPlanId);
    }

    public YearPlan createYearPlan(long scheduleId, long yearId, YearPlanInputDTO yearPlanInputDTO) {
        Year year = yearService.getYearById(yearId);
        checkIfInputDataFieldsAreValid(yearPlanInputDTO, year);
        return save(new YearPlan(yearPlanInputDTO.getContent(),
                                        yearPlanInputDTO.getStartDate(),
                                        yearPlanInputDTO.getEndDate(),
                                        yearPlanInputDTO.getImportance(),
                                        yearPlanInputDTO.getUrgency(),
                                        scheduleService.getScheduleById(scheduleId),
                                        year));
    }

    public YearPlan updateYearPlan(long scheduleId, long yearPlanId, YearPlanInputDTO yearPlanInputDTO) {
        YearPlan yearPlan = getAuthorizedNotNullYearPlanById(scheduleId, yearPlanId);
        Year year = yearPlan.getYear();
        checkIfInputDataFieldsAreValid(yearPlanInputDTO, year);
        updateYearPlanAttributes(yearPlanInputDTO, yearPlan);
        return save(yearPlan);
    }

    private void checkIfInputDataFieldsAreValid(YearPlanInputDTO yearPlanInputDTO, Year year) {
        int yearNumber = year.getYearNumber();
        if (yearPlanInputDTO.getStartDate().getYear() != yearNumber)
            throw new IllegalArgumentException("Start date cannot include another year. ");
        if (yearPlanInputDTO.getEndDate().getYear() != yearNumber)
            throw new IllegalArgumentException("End date cannot include another year. ");
    }

    public String deleteYearPlan(long scheduleId, long yearPlanId) {
        delete(getAuthorizedNotNullYearPlanById(scheduleId, yearPlanId));
        return "Plan deleted successfully. ";
    }

    public YearPlan updateFulfilledStatus(long scheduleId, long yearPlanId) {
        YearPlan yearPlan = getAuthorizedNotNullYearPlanById(scheduleId, yearPlanId);
        yearPlan.setFulfilled(!yearPlan.isFulfilled());
        return save(yearPlan);
    }

    private YearPlan getAuthorizedNotNullYearPlanById(long scheduleId, long yearPlanId) {
        YearPlan yearPlan = repository.findById(yearPlanId).orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, yearPlan));
        return yearPlan;
    }

    private YearPlan save(YearPlan yearPlan) {
        return repository.save(yearPlan);
    }

    private void updateYearPlanAttributes(YearPlanInputDTO yearPlanInputDTO, YearPlan yearPlan) {
        yearPlan.setContent(yearPlanInputDTO.getContent());
        yearPlan.setStartDate(yearPlanInputDTO.getStartDate());
        yearPlan.setEndDate(yearPlanInputDTO.getEndDate());
        if(yearPlanInputDTO.getImportance() != null)
            yearPlan.setImportance(yearPlanInputDTO.getImportance());
        if(yearPlanInputDTO.getUrgency() != null)
            yearPlan.setUrgency(yearPlanInputDTO.getUrgency());
    }

    private void delete(YearPlan yearPlan) {
        repository.delete(yearPlan);
    }
}
