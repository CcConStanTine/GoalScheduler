package com.pk.ms.controllers.week;

import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.services.week.WeekSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class WeekSummaryController {

    private final WeekSummaryService weekSummaryService;

    public WeekSummaryController(WeekSummaryService weekSummaryService) {
        this.weekSummaryService = weekSummaryService;
    }

    // create a new WeekSummary
    @PostMapping(value="/schedules/{schedule_id}/weeks/{week_id}/week_summary")
    public ResponseEntity<WeekSummary> createWeekSummary(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("week_id") long weekId) {

        return ResponseEntity.ok(weekSummaryService.createWeekSummary(scheduleId, weekId));
    }

    // get particular WeekSummary
    @GetMapping("/schedules/{schedule_id}/week_summary/{week_summary_id}")
    public ResponseEntity<WeekSummary> getWeekSummary(@PathVariable("schedule_id") long scheduleId,
                                                      @PathVariable("week_summary_id") long weekSummaryId) {

        return ResponseEntity.ok(weekSummaryService.getWeekSummary(scheduleId, weekSummaryId));
    }
}
