package com.pk.ms.services.year;

import com.pk.ms.abstracts.SummaryAccessAuthorizationService;
import com.pk.ms.dao.year.YearSummaryRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearSummary;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class YearSummaryService implements SummaryAccessAuthorizationService {

    private final YearSummaryRepository repository;

    private final YearService yearService;

    private final ScheduleService scheduleService;

    public YearSummaryService(YearSummaryRepository repository, YearService yearService,
                              ScheduleService scheduleService) {
        this.repository = repository;
        this.yearService = yearService;
        this.scheduleService = scheduleService;
    }

    public YearSummary getYearSummaryById(long scheduleId, long yearSummaryId) {
        return getAuthorizedNotNullYearSummaryById(scheduleId, yearSummaryId);
    }

    public YearSummary getYearSummaryByScheduleIdAndYearId(long scheduleId, long yearId) {
        return getAuthorizedNotNullYearSummaryByScheduleIdAndYearId(scheduleId, yearId);
    }

    public YearSummary createYearSummary(long scheduleId, long yearId) {
        Year year = yearService.getYearById(yearId);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        return saveYearSummary(new YearSummary(schedule, year));
    }

    public YearSummary updateYearSummary(long scheduleId, long yearSummaryId) {
        YearSummary yearSummary = getAuthorizedNotNullYearSummaryById(scheduleId, yearSummaryId);
        updateYearSummary(yearSummary);
        return yearSummary;
    }

    private YearSummary getAuthorizedNotNullYearSummaryById(long scheduleId, long yearSummaryId) {
        YearSummary yearSummary = repository.findById(yearSummaryId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, yearSummary));
        return yearSummary;
    }

    private YearSummary getAuthorizedNotNullYearSummaryByScheduleIdAndYearId(long scheduleId, long yearId) {
        YearSummary yearSummary = repository.findByScheduleIdAndYearId(scheduleId, yearId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, yearSummary));
        return yearSummary;
    }

    private YearSummary saveYearSummary(YearSummary yearSummary) {
        return repository.save(yearSummary);
    }

    private void updateYearSummary(YearSummary yearSummary) {
        yearSummary.countFulfilledAmount();
        yearSummary.countFailedAmount();
    }
}
