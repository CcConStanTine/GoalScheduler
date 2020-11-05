package com.pk.ms.controllers.month;

import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.services.month.MonthSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class MonthSummaryController {

    private final MonthSummaryService monthSummaryService;

    public MonthSummaryController(MonthSummaryService monthSummaryService) {
        this.monthSummaryService = monthSummaryService;
    }

    @GetMapping("/month_summary/{month_summary_id}")
    public ResponseEntity<MonthSummary> getMonthSummaryById(@PathVariable("schedule_id") long scheduleId,
                                                            @PathVariable("month_summary_id") long monthSummaryId) {

        return ResponseEntity.ok(monthSummaryService.getMonthSummaryById(scheduleId, monthSummaryId));
    }

    @GetMapping("/month/{month_id}/month_summary")
    public ResponseEntity<MonthSummary> getMonthSummaryByMonthId(@PathVariable("schedule_id") long scheduleId,
                                                                 @PathVariable("month_id") long monthId) {

        return ResponseEntity.ok(monthSummaryService.getMonthSummaryByScheduleIdAndMonthId(scheduleId, monthId));
    }

    @PostMapping("/month/{month_id}/month_summary")
    public ResponseEntity<MonthSummary> createMonthSummary(@PathVariable("schedule_id") long scheduleId,
                                                           @PathVariable("month_id") long monthId) {

        return ResponseEntity.ok(monthSummaryService.createMonthSummary(scheduleId, monthId));
    }
}