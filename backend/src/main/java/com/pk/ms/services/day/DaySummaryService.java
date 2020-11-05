package com.pk.ms.services.day;

import com.pk.ms.dao.day.DaySummaryRepository;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryService {

    private final DaySummaryRepository daySummaryRepo;

    private final DayService dayService;

    private final ScheduleService scheduleService;

    public DaySummaryService(DaySummaryRepository daySummaryRepo, DayService dayService, ScheduleService scheduleService) {
        this.daySummaryRepo = daySummaryRepo;
        this.dayService = dayService;
        this.scheduleService = scheduleService;
    }

    public DaySummary getDaySummaryById(long scheduleId, long daySummaryId) {
        DaySummary daySummary = getNotNullDaySummaryById(daySummaryId);
        if(hasAccess(scheduleId, daySummary))
            return daySummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public DaySummary getDaySummaryByScheduleIdAndDayId(long scheduleId, long dayId) {
        DaySummary daySummary = getNotNullDaySummaryByScheduleIdAndDayId(scheduleId, dayId);
        if(hasAccess(scheduleId, daySummary))
            return daySummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public DaySummary createDaySummary(long scheduleId, long dayId) {
        DaySummary daySummary = getNotNullDaySummaryByScheduleIdAndDayId(scheduleId, dayId);
        if(isObjectNull(daySummary)) {
            Day day = dayService.getDayById(dayId);
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            return saveDaySummary(new DaySummary(day, schedule));
        }
        else {
            return updateDaySummary(scheduleId, daySummary);
        }
    }


    private DaySummary getNotNullDaySummaryById(long daySummaryId) {
        DaySummary daySummary = getDaySummaryByIdFromRepo(daySummaryId);
        if(isObjectNull(daySummary))
            throw new ResourceNotAvailableException();
        return daySummary;
    }

    private DaySummary getDaySummaryByIdFromRepo(long daySummaryId) {
        return daySummaryRepo.findById(daySummaryId);
    }

    private DaySummary getNotNullDaySummaryByScheduleIdAndDayId(long scheduleId, long dayId) {
        DaySummary daySummary = getDaySummaryByScheduleIdAndDayIdFromRepo(scheduleId, dayId);
        if(isObjectNull(daySummary))
            throw new ResourceNotAvailableException();
        return daySummary;
    }

    private DaySummary getDaySummaryByScheduleIdAndDayIdFromRepo(long scheduleId, long dayId) {
        return daySummaryRepo.findByScheduleIdAndDayId(scheduleId, dayId);
    }

    private DaySummary saveDaySummary(DaySummary daySummary) {
        return daySummaryRepo.save(daySummary);
    }

    private DaySummary updateDaySummary(long scheduleId, DaySummary daySummary) {
        if (hasAccess(scheduleId, daySummary)) {
            daySummary.countFulfilledAmount();
            daySummary.countFailedAmount();
            return daySummary;
        }
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, DaySummary daySummary) {
        return daySummary.getSchedule().getScheduleId() == scheduleId;
    }
}
