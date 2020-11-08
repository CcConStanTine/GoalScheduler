package com.pk.ms.controllers.week;

import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.services.week.WeekSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class WeekSummaryController {

    private final WeekSummaryService weekSummaryService;

    public WeekSummaryController(WeekSummaryService weekSummaryService) {
        this.weekSummaryService = weekSummaryService;
    }

    @GetMapping("/week_summary/{week_summary_id}")
    public ResponseEntity<WeekSummary> getWeekSummaryById(@PathVariable("schedule_id") long scheduleId,
                                                          @PathVariable("week_summary_id") long weekSummaryId) {

        return ResponseEntity.ok(weekSummaryService.getWeekSummaryById(scheduleId, weekSummaryId));
    }

    @GetMapping("/week/{week_id}/week_summary")
    public ResponseEntity<WeekSummary> getWeekSummaryByWeekId(@PathVariable("schedule_id") long scheduleId,
                                                              @PathVariable("week_id") long weekId) {

        return ResponseEntity.ok(weekSummaryService.getWeekSummaryByScheduleIdAndWeekId(scheduleId, weekId));
    }

    @PostMapping("/week/{week_id}/week_summary")
    public ResponseEntity<WeekSummary> createWeekSummary(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("week_id") long weekId) {

        return ResponseEntity.ok(weekSummaryService.createWeekSummary(scheduleId, weekId));
    }
}
