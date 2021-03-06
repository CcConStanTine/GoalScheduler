package com.pk.ms.services.month;

import com.pk.ms.abstracts.SummaryAccessAuthorizationService;
import com.pk.ms.dao.month.MonthSummaryRepository;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class MonthSummaryService implements SummaryAccessAuthorizationService {

    private final MonthSummaryRepository repository;

    private final MonthService monthService;

    private final ScheduleService scheduleService;

    public MonthSummaryService(MonthSummaryRepository repository, MonthService monthService, ScheduleService scheduleService) {
        this.repository = repository;
        this.monthService = monthService;
        this.scheduleService = scheduleService;
    }

    public MonthSummary getMonthSummaryById(long scheduleId, long monthSummaryId) {
        return getAuthorizedNotNullMonthSummaryById(scheduleId, monthSummaryId);
    }

    public MonthSummary getMonthSummaryByScheduleIdAndMonthId(long scheduleId, long monthId) {
        return getAuthorizedNotNullMonthSummaryByScheduleIdAndMonthId(scheduleId, monthId);
    }

    public MonthSummary createMonthSummary(long scheduleId, long monthId) {
        Month month = monthService.getMonthById(monthId);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        return save(new MonthSummary(schedule, month));
    }

    public MonthSummary updateMonthSummary(long scheduleId, long monthSummaryId) {
        MonthSummary monthSummary = getAuthorizedNotNullMonthSummaryById(scheduleId, monthSummaryId);
        monthSummary.countSummary();
        return save(monthSummary);
    }

    private MonthSummary getAuthorizedNotNullMonthSummaryById(long scheduleId, long monthSummaryId) {
        MonthSummary monthSummary = repository.findById(monthSummaryId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, monthSummary));
        return monthSummary;
    }

    private MonthSummary getAuthorizedNotNullMonthSummaryByScheduleIdAndMonthId(long scheduleId, long monthId) {
        return repository.findByScheduleIdAndMonthId(scheduleId, monthId)
                .orElseThrow(ResourceNotAvailableException::new);
    }

    private MonthSummary save(MonthSummary monthSummary) {
        return repository.save(monthSummary);
    }
}

