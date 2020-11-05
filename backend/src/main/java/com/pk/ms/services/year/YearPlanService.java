package com.pk.ms.services.year;

import com.pk.ms.dao.year.YearPlanRepository;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class YearPlanService {

    private final YearPlanRepository yearPlanRepo;

    private final YearService yearService;

    private final ScheduleService scheduleService;

    public YearPlanService(YearPlanRepository yearPlanRepo, YearService yearService, ScheduleService scheduleService) {
        this.yearPlanRepo = yearPlanRepo;
        this.yearService = yearService;
        this.scheduleService = scheduleService;
    }

    public List<YearPlan> getYearPlansByScheduleIdAndYearId(long scheduleId, long yearId) {
        return getYearPlansByScheduleIdAndYearIdFromRepo(scheduleId, yearId);
    }

    public YearPlan createYearPlan(long scheduleId, long yearId, YearPlanInputDTO yearPlanInputDTO) {
        return saveYearPlan(new YearPlan(yearPlanInputDTO.getContent(),
                yearPlanInputDTO.getStartDate(),
                yearPlanInputDTO.getEndDate(),
                yearService.getYearById(yearId),
                scheduleService.getScheduleById(scheduleId)));
    }

    public YearPlan updateYearPlan(long scheduleId, long yearPlanId, YearPlanInputDTO yearPlanInputDTO) {

        YearPlan yearPlan = getYearPlanById(yearPlanId);

        if(hasAccess(scheduleId, yearPlan)) {

            String content = yearPlanInputDTO.getContent();
            if (isObjectNull(content))
                yearPlan.setContent(content);

            LocalDate startDate = yearPlanInputDTO.getStartDate();
            if (isObjectNull(startDate))
                yearPlan.setStartDate(startDate);

            LocalDate endDate = yearPlanInputDTO.getEndDate();
            if (isObjectNull(endDate))
                yearPlan.setEndDate(endDate);

            return saveYearPlan(yearPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public String deleteYearPlan(long scheduleId, long yearPlanId) {
        YearPlan yearPlan = getYearPlanById(yearPlanId);
        if(hasAccess(scheduleId, yearPlan)) {
            deleteYearPlan(yearPlan);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public YearPlan updateFulfilledStatus(long scheduleId, long yearPlanId) {
        YearPlan yearPlan = getYearPlanById(yearPlanId);
        if(hasAccess(scheduleId, yearPlan)) {
            boolean fulfilled = yearPlan.isFulfilled();
            fulfilled = !fulfilled;
            yearPlan.setFulfilled(fulfilled);
            return saveYearPlan(yearPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }


    private List<YearPlan> getYearPlansByScheduleIdAndYearIdFromRepo(long scheduleId, long yearId) {
        return yearPlanRepo.findYearPlansByScheduleIdAndYearId(scheduleId, yearId);
    }

    private YearPlan getNotNullYearPlanById(long id) {
        YearPlan yearPlan = yearPlanRepo.findById(id);
        if(isObjectNull(yearPlan))
            throw new ResourceNotAvailableException();
        return yearPlan;
    }

    private YearPlan getYearPlanById(long id) {
        return getNotNullYearPlanById(id);
    }

    private YearPlan saveYearPlan(YearPlan yearPlan) {
        return yearPlanRepo.save(yearPlan);
    }

    private void deleteYearPlan(YearPlan yearPlan) {
        yearPlanRepo.delete(yearPlan);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, YearPlan yearPlan) {
        return yearPlan.getSchedule().getScheduleId() == scheduleId;
    }
}
