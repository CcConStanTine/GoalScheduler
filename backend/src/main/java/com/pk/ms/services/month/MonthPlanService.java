package com.pk.ms.services.month;

import com.pk.ms.dao.month.MonthPlanRepository;
import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonthPlanService {

    private final MonthPlanRepository monthPlanRepo;

    private final MonthService monthService;

    private final ScheduleService scheduleService;

    public MonthPlanService(MonthPlanRepository monthPlanRepo, MonthService monthService, ScheduleService scheduleService) {
        this.monthPlanRepo = monthPlanRepo;
        this.monthService = monthService;
        this.scheduleService = scheduleService;
    }

    public List<MonthPlan> getMonthPlansByScheduleIdAndMonthId(long scheduleId, long monthId) {
        return getMonthPlansByScheduleIdAndMonthIdFromRepo(scheduleId, monthId);
    }

    public MonthPlan getMonthPlan(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = getNotNullMonthPlanById(monthPlanId);
        if(hasAccess(scheduleId, monthPlan))
            return monthPlan;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public MonthPlan createMonthPlan(long scheduleId, long monthId, MonthPlanInputDTO monthPlanInputDTO) {
        return saveMonthPlan(new MonthPlan(monthPlanInputDTO.getContent(),
                monthPlanInputDTO.getStartDate(),
                monthPlanInputDTO.getEndDate(),
                monthService.getMonthById(monthId),
                scheduleService.getScheduleById(scheduleId)));
    }

    public MonthPlan updateMonthPlan(long scheduleId, long monthPlanId, MonthPlanInputDTO monthPlanInputDTO) {

        MonthPlan monthPlan = getMonthPlanById(monthPlanId);

        if(hasAccess(scheduleId, monthPlan)) {

            String content = monthPlanInputDTO.getContent();
            if (!isObjectNull(content))
                monthPlan.setContent(content);

            LocalDate startDate = monthPlanInputDTO.getStartDate();
            if (!isObjectNull(startDate))
                monthPlan.setStartDate(startDate);

            LocalDate endDate = monthPlanInputDTO.getEndDate();
            if (!isObjectNull(endDate))
                monthPlan.setEndDate(endDate);

            return saveMonthPlan(monthPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public String deleteMonthPlan(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = getMonthPlanById(monthPlanId);
        if(hasAccess(scheduleId, monthPlan)) {
            deleteMonthPlan(monthPlan);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public MonthPlan updateFulfilledStatus(long scheduleId, long monthPlanId) {
        MonthPlan monthPlan = getMonthPlanById(monthPlanId);
        if(hasAccess(scheduleId, monthPlan)) {
            boolean fulfilled = monthPlan.isFulfilled();
            fulfilled = !fulfilled;
            monthPlan.setFulfilled(fulfilled);
            return saveMonthPlan(monthPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }


    private List<MonthPlan> getMonthPlansByScheduleIdAndMonthIdFromRepo(long scheduleId, long monthId) {
        return monthPlanRepo.findMonthPlansByScheduleIdAndMonthId(scheduleId, monthId);
    }

    private MonthPlan getNotNullMonthPlanById(long id) {
        MonthPlan monthPlan = monthPlanRepo.findById(id);
        if(isObjectNull(monthPlan))
            throw new ResourceNotAvailableException();
        return monthPlan;
    }

    private MonthPlan getMonthPlanById(long monthPlanId) {
        return getNotNullMonthPlanById(monthPlanId);
    }

    private MonthPlan saveMonthPlan(MonthPlan monthPlan) {
        return monthPlanRepo.save(monthPlan);
    }

    private void deleteMonthPlan(MonthPlan monthPlan) {
        monthPlanRepo.delete(monthPlan);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, MonthPlan monthPlan) {
        return monthPlan.getSchedule().getScheduleId() == scheduleId;
    }
}
