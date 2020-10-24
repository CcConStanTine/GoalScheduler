package com.pk.ms.controllers.month;

import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.services.month.MonthSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class MonthSummaryController {

    private final MonthSummaryService monthSummaryService;

    public MonthSummaryController(MonthSummaryService monthSummaryService) {
        this.monthSummaryService = monthSummaryService;
    }

    // create a new MonthSummary
    @PostMapping(value="/schedules/{schedule_id}/months/{month_id}/month_summary")
    public ResponseEntity<MonthSummary> createYearSummary(@PathVariable("schedule_id") long scheduleId,
                                                          @PathVariable("month_id") long monthId) {
        return ResponseEntity.ok(monthSummaryService.createMonthSummary(scheduleId, monthId));
    }

    // get particular MonthSummary
    @GetMapping("/schedules/{schedule_id}/month_summary/{month_summary_id}")
    public ResponseEntity<MonthSummary> getYearSummary(@PathVariable("schedule_id") long scheduleId,
                                                       @PathVariable("month_summary_id") long monthSummaryId) {
        return ResponseEntity.ok(monthSummaryService.getMonthSummary(scheduleId, monthSummaryId));
    }
}
