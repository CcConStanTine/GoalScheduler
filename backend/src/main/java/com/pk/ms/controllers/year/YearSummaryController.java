package com.pk.ms.controllers.year;

import com.pk.ms.entities.year.YearSummary;
import com.pk.ms.services.year.YearSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class YearSummaryController {

    private final YearSummaryService yearSummaryService;

    public YearSummaryController(YearSummaryService yearSummaryService) {
        this.yearSummaryService = yearSummaryService;
    }

    @GetMapping("/year_summary/{year_summary_id}")
    public ResponseEntity<YearSummary> getYearSummaryById(@PathVariable("schedule_id") long scheduleId,
                                                          @PathVariable("year_summary_id") long yearSummaryId) {

        return ResponseEntity.ok(yearSummaryService.getYearSummaryById(scheduleId, yearSummaryId));
    }

    @GetMapping("/year/{year_id}/year_summary")
    public ResponseEntity<YearSummary> getYearSummaryByYearId(@PathVariable("schedule_id") long scheduleId,
                                                              @PathVariable("year_id") long yearId) {

        return ResponseEntity.ok(yearSummaryService.getYearSummaryByScheduleIdAndYearId(scheduleId, yearId));
    }

    @PostMapping("/year/{year_id}/year_summary")
    public ResponseEntity<YearSummary> createYearSummary(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("year_id") long yearId) {

        return ResponseEntity.ok(yearSummaryService.createYearSummary(scheduleId, yearId));
    }

    @PatchMapping("/year_summary/{year_summary_id}")
    public ResponseEntity<YearSummary> updateYearSummary(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("year_summary_id") long yearSummaryId) {

        return ResponseEntity.ok(yearSummaryService.updateYearSummary(scheduleId, yearSummaryId));
    }

}
