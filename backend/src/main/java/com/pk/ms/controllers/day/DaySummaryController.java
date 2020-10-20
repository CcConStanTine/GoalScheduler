package com.pk.ms.controllers.day;

import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.services.day.DaySummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class DaySummaryController {


    private final DaySummaryService daySummaryService;

    public DaySummaryController(DaySummaryService daySummaryService) {
        this.daySummaryService = daySummaryService;
    }

    // create a new DaySummary
    @PostMapping(value="/days/{day_id}/day_summary")
    @ResponseStatus(HttpStatus.OK)
    public DaySummary createYearSummary(@PathVariable("day_id") long dayId) {
        return daySummaryService.createDaySummary(dayId);
    }

    // get particular DaySummary
    @GetMapping("/day_summary/{day_summary_id}")
    @ResponseStatus(HttpStatus.OK)
    public DaySummary getDaySummary(@PathVariable("day_summary_id") long id) {
        return daySummaryService.getDaySummaryById(id);
    }
}
