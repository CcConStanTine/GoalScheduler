package com.pk.ms.services.year;

import com.pk.ms.dao.year.IYearPlanRepository;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class YearPlanService {

    private final IYearPlanRepository yearPlanRepo;

    private final YearService yearService;

    public YearPlanService(IYearPlanRepository yearPlanRepo, YearService yearService) {
        this.yearPlanRepo = yearPlanRepo;
        this.yearService = yearService;
    }

    public YearPlan saveYearPlan(YearPlan yearPlan) {
        return yearPlanRepo.save(yearPlan);
    }

    public YearPlan getYearPlanById(long id) {
        return yearPlanRepo.findById(id);
    }

    public String deleteYearPlan(long scheduleId, long yearPlanId) {
        YearPlan yearPlan = getYearPlanById(yearPlanId);
        if(yearPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, yearPlan)) {
            yearPlanRepo.deleteById(yearPlanId);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public YearPlan createYearPlan(long scheduleId, long yearId, YearPlanInputDTO yearPlanInputDTO) {
        Year year = yearService.getYearById(yearId);
        if(year == null)
            throw new ResourceNotAvailableException();
        if(yearService.hasAccess(scheduleId, year))
            return saveYearPlan(new YearPlan(yearPlanInputDTO.getContent(), yearPlanInputDTO.getStartDate(),
                    yearPlanInputDTO.getEndDate(), year));
        else
            throw new AccessDeniedException("This user cannot create plan in this Year. ");
    }

    public YearPlan updateYearPlan(long scheduleId, long yearPlanId, YearPlanInputDTO yearPlanInputDTO) {

        YearPlan yearPlan = getYearPlanById(yearPlanId);
        if(yearPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, yearPlan)) {
            if (yearPlanInputDTO.getContent() != null)
                yearPlan.setContent(yearPlanInputDTO.getContent());
            if (yearPlanInputDTO.getStartDate() != null)
                yearPlan.setStartDate(yearPlanInputDTO.getStartDate());
            if (yearPlanInputDTO.getEndDate() != null)
                yearPlan.setEndDate(yearPlanInputDTO.getEndDate());

            return saveYearPlan(yearPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public YearPlan updateFulfilledStatus(long scheduleId, long yearPlanId) {
        YearPlan yearPlan = getYearPlanById(yearPlanId);
        if(yearPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, yearPlan)) {
            boolean fullfilled = yearPlan.isFulfilled();
            if (!fullfilled)
                fullfilled = true;
            else
                fullfilled = false;
            yearPlan.setFulfilled(fullfilled);
            return saveYearPlan(yearPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public boolean hasAccess(long scheduleId, YearPlan yearPlan) {
        return yearPlan.getYear().getSchedule().getScheduleId() == scheduleId;
    }
}
