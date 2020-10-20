package com.pk.ms.controllers.month;

import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.services.month.MonthSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MonthSummaryController {

    private final MonthSummaryService monthSummaryService;

    public MonthSummaryController(MonthSummaryService monthSummaryService) {
        this.monthSummaryService = monthSummaryService;
    }

    // create a new MonthSummary
    @PostMapping(value="/months/{month_id}/month_summary")
    public ResponseEntity<MonthSummary> createYearSummary(@PathVariable("month_id") long monthId) {
        return ResponseEntity.ok(monthSummaryService.createMonthSummary(monthId));
    }

    // get particular MonthSummary
    @GetMapping("/month_summary/{month_summary_id}")
    public ResponseEntity<MonthSummary> getYearSummary(@PathVariable("month_summary_id") long id) {
        return ResponseEntity.ok(monthSummaryService.getMonthSummaryById(id));
    }


}
