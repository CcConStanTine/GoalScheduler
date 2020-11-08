package com.pk.ms.services.year;

import com.pk.ms.dao.year.YearSummaryRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearSummary;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class YearSummaryService {

    private final YearSummaryRepository yearSummaryRepo;

    private final YearService yearService;

    private final ScheduleService scheduleService;

    public YearSummaryService(YearSummaryRepository yearSummaryRepo, YearService yearService,
                              ScheduleService scheduleService) {
        this.yearSummaryRepo = yearSummaryRepo;
        this.yearService = yearService;
        this.scheduleService = scheduleService;
    }

    public YearSummary getYearSummaryById(long scheduleId, long yearSummaryId) {
        YearSummary yearSummary = getNotNullYearSummaryById(yearSummaryId);
        if(hasAccess(scheduleId, yearSummary))
            return yearSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public YearSummary getYearSummaryByScheduleIdAndYearId(long scheduleId, long yearId) {
        YearSummary yearSummary = getNotNullYearSummaryByScheduleIdAndYearId(scheduleId, yearId);
        if(hasAccess(scheduleId, yearSummary))
            return yearSummary;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public YearSummary createYearSummary(long scheduleId, long yearId) {
        YearSummary yearSummary = getYearSummaryByScheduleIdAndYearIdFromRepo(scheduleId, yearId);
        if(isObjectNull(yearSummary)) {
            Year year = yearService.getYearById(yearId);
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            return saveYearSummary(new YearSummary(year, schedule));
        }
        else {
            updateYearSummary(scheduleId, yearSummary);
            return saveYearSummary(yearSummary);
        }
    }



    private YearSummary getNotNullYearSummaryById(long yearSummaryId) {
        YearSummary yearSummary = getYearSummaryByIdFromRepo(yearSummaryId);
        if(isObjectNull(yearSummary))
            throw new ResourceNotAvailableException();
        return yearSummary;
    }

    private YearSummary getYearSummaryByIdFromRepo(long yearSummaryId) {
        return yearSummaryRepo.findById(yearSummaryId);
    }

    private YearSummary getNotNullYearSummaryByScheduleIdAndYearId(long scheduleId, long yearId) {
        YearSummary yearSummary = getYearSummaryByScheduleIdAndYearIdFromRepo(scheduleId, yearId);
        if(isObjectNull(yearSummary))
            throw new ResourceNotAvailableException();
        return yearSummary;
    }

    private YearSummary getYearSummaryByScheduleIdAndYearIdFromRepo(long scheduleId, long yearId) {
        return yearSummaryRepo.findByScheduleIdAndYearId(scheduleId, yearId);
    }

    private YearSummary saveYearSummary(YearSummary yearSummary) {
        return yearSummaryRepo.save(yearSummary);
    }

    private void updateYearSummary(long scheduleId, YearSummary yearSummary) {
        if (hasAccess(scheduleId, yearSummary)) {
            yearSummary.countFulfilledAmount();
            yearSummary.countFailedAmount();
        }
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, YearSummary yearSummary) {
        return yearSummary.getSchedule().getScheduleId() == scheduleId;
    }

}
