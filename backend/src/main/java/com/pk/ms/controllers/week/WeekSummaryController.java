package com.pk.ms.controllers.week;

import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.services.week.WeekSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeekSummaryController {

    private final WeekSummaryService weekSummaryService;

    public WeekSummaryController(WeekSummaryService weekSummaryService) {
        this.weekSummaryService = weekSummaryService;
    }

    // create a new WeekSummary
    @PostMapping(value="/weeks/{week_id}/week_summary")
    public ResponseEntity<WeekSummary> createWeekSummary(@PathVariable("week_id") long weekId) {
        return ResponseEntity.ok(weekSummaryService.createWeekSummary(weekId));
    }

    // get particular WeekSummary
    @GetMapping("/week_summary/{week_summary_id}")
    public ResponseEntity<WeekSummary> getWeekSummary(@PathVariable("week_summary_id") long id) {
        return ResponseEntity.ok(weekSummaryService.getWeekSummaryById(id));
    }
}
