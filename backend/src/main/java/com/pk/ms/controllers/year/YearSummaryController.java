package com.pk.ms.controllers.year;

import com.pk.ms.entities.year.YearSummary;
import com.pk.ms.services.year.YearSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class YearSummaryController {

    private final YearSummaryService yearSummaryService;

    public YearSummaryController(YearSummaryService yearSummaryService) {
        this.yearSummaryService = yearSummaryService;
    }

    // create a new YearSummary
    @PostMapping("/years/{year_id}/year_summary")
    public ResponseEntity<YearSummary> createYearSummary(@PathVariable("year_id") long yearId) {
        return ResponseEntity.ok(yearSummaryService.createYearSummary(yearId));
    }

    // get particular YearSummary
    @GetMapping("/year_summary/{year_summary_id}")
    public ResponseEntity<YearSummary> getYearSummary(@PathVariable("year_summary_id") long id) {
        return ResponseEntity.ok(yearSummaryService.getYearSummaryById(id));
    }

}
