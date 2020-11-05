package com.pk.ms.services.month;

import com.pk.ms.dao.month.MonthSummaryRepository;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class MonthSummaryService {

    private final MonthSummaryRepository monthSummaryRepo;

    private final MonthService monthService;

    private final ScheduleService scheduleService;

    public MonthSummaryService(MonthSummaryRepository monthSummaryRepo, MonthService monthService, ScheduleService scheduleService) {
        this.monthSummaryRepo = monthSummaryRepo;
        this.monthService = monthService;
        this.scheduleService = scheduleService;
    }

    public MonthSummary getMonthSummaryById(long scheduleId, long monthSummaryId) {
        MonthSummary monthSummary = getNotNullMonthSummaryById(monthSummaryId);
        if(hasAccess(scheduleId, monthSummary))
            return monthSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public MonthSummary getMonthSummaryByScheduleIdAndMonthId(long scheduleId, long monthId) {
        MonthSummary monthSummary = getNotNullMonthSummaryByScheduleIdAndMonthId(scheduleId, monthId);
        if(hasAccess(scheduleId, monthSummary))
            return monthSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public MonthSummary createMonthSummary(long scheduleId, long monthId) {
        MonthSummary monthSummary = getNotNullMonthSummaryByScheduleIdAndMonthId(scheduleId, monthId);
        if(isObjectNull(monthSummary)) {
            Month month = monthService.getMonthById(monthId);
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            return saveMonthSummary(new MonthSummary(month, schedule));
        }
        else {
            return updateYMonthSummary(scheduleId, monthSummary);
        }
    }


    private MonthSummary getNotNullMonthSummaryById(long monthSummaryId) {
        MonthSummary monthSummary = getMonthSummaryByIdFromRepo(monthSummaryId);
        if(isObjectNull(monthSummary))
            throw new ResourceNotAvailableException();
        return monthSummary;
    }

    private MonthSummary getMonthSummaryByIdFromRepo(long monthSummaryId) {
        return monthSummaryRepo.findById(monthSummaryId);
    }

    private MonthSummary getNotNullMonthSummaryByScheduleIdAndMonthId(long scheduleId, long monthId) {
        MonthSummary monthSummary = getMonthSummaryByScheduleIdAndMonthIdFromRepo(scheduleId, monthId);
        if(isObjectNull(monthSummary))
            throw new ResourceNotAvailableException();
        return monthSummary;
    }

    private MonthSummary getMonthSummaryByScheduleIdAndMonthIdFromRepo(long scheduleId, long monthId) {
        return monthSummaryRepo.findByScheduleIdAndMonthId(scheduleId, monthId);
    }

    private MonthSummary saveMonthSummary(MonthSummary monthSummary) {
        return monthSummaryRepo.save(monthSummary);
    }

    private MonthSummary updateYMonthSummary(long scheduleId, MonthSummary monthSummary) {
        if (hasAccess(scheduleId, monthSummary)) {
            monthSummary.countFulfilledAmount();
            monthSummary.countFailedAmount();
            return monthSummary;
        }
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, MonthSummary monthSummary) {
        return monthSummary.getSchedule().getScheduleId() == scheduleId;
    }

}

