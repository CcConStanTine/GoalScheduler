package com.pk.ms.controllers.year;

import com.pk.ms.entities.year.YearSummary;
import com.pk.ms.services.year.YearSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class YearSummaryController {

    private final YearSummaryService yearSummaryService;

    public YearSummaryController(YearSummaryService yearSummaryService) {
        this.yearSummaryService = yearSummaryService;
    }

    // create a new YearSummary
    @PostMapping("/schedules/{schedule_id}/years/{year_id}/year_summary")
    public ResponseEntity<YearSummary> createYearSummary(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("year_id") long yearId) {
        return ResponseEntity.ok(yearSummaryService.createYearSummary(scheduleId, yearId));
    }

    // get particular YearSummary
    @GetMapping("/schedules/{schedule_id}/year_summary/{year_summary_id}")
    public ResponseEntity<YearSummary> getYearSummary(@PathVariable("schedule_id") long scheduleId,
                                                      @PathVariable("year_summary_id") long id) {
        return ResponseEntity.ok(yearSummaryService.getYearSummary(scheduleId, id));
    }

}
