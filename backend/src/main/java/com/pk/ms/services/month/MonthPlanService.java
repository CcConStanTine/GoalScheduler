package com.pk.ms.services.month;

import com.pk.ms.dao.month.IMonthPlanRepository;
import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class MonthPlanService {

    private final IMonthPlanRepository monthPlanRepo;

    private final MonthService monthService;

    public MonthPlanService(IMonthPlanRepository monthPlanRepo, MonthService monthService) {
        this.monthPlanRepo = monthPlanRepo;
        this.monthService = monthService;
    }

    public MonthPlan saveMonthPlan(MonthPlan monthPlan) { return monthPlanRepo.save(monthPlan); }

    public MonthPlan getMonthPlanById(long id) { return monthPlanRepo.findById(id); }

    public String deleteMonthPlan(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = getMonthPlanById(monthPlanId);
        if(monthPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, monthPlan)) {
            monthPlanRepo.deleteById(monthPlanId);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public MonthPlan createMonthPlan(long scheduleId, long monthId, MonthPlanInputDTO monthPlanInputDTO) {
        Month month = monthService.getMonthById(monthId);
        if(month == null)
            throw new ResourceNotAvailableException();
        if(monthService.hasAccess(scheduleId, month))
            return saveMonthPlan(new MonthPlan(monthPlanInputDTO.getContent(), monthPlanInputDTO.getStartDate(),
                monthPlanInputDTO.getEndDate(), month));
        else
            throw new AccessDeniedException("This user cannot create plan in this Month. ");
    }

    public MonthPlan updateMonthPlan(long scheduleId, long monthPlanId, MonthPlanInputDTO monthPlanInputDTO) {
        MonthPlan monthPlan = getMonthPlanById(monthPlanId);
        if(monthPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, monthPlan)) {
            if (monthPlanInputDTO.getContent() != null)
                monthPlan.setContent(monthPlanInputDTO.getContent());
            if (monthPlanInputDTO.getStartDate() != null)
                monthPlan.setStartDate(monthPlanInputDTO.getStartDate());
            if (monthPlanInputDTO.getEndDate() != null)
                monthPlan.setEndDate(monthPlanInputDTO.getEndDate());

            return saveMonthPlan(monthPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public MonthPlan updateFulfilledStatus(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = getMonthPlanById(monthPlanId);
        if(monthPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, monthPlan)) {
            boolean fullfilled = monthPlan.isFulfilled();
            if (!fullfilled)
                fullfilled = true;
            else
                fullfilled = false;
            monthPlan.setFulfilled(fullfilled);
            return saveMonthPlan(monthPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }
    public boolean hasAccess(long scheduleId, MonthPlan monthPlan) {
        return monthPlan.getMonth().getYear().getSchedule().getScheduleId() == scheduleId;
    }
}
