package com.pk.ms.controllers.day;

import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.services.day.DaySummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class DaySummaryController {


    private final DaySummaryService daySummaryService;

    public DaySummaryController(DaySummaryService daySummaryService) {
        this.daySummaryService = daySummaryService;
    }

    // create a new DaySummary
    @PostMapping(value="/schedules/{schedule_id}/days/{day_id}/day_summary")
    @ResponseStatus(HttpStatus.OK)
    public DaySummary createYearSummary(@PathVariable("schedule_id") long scheduleId,
                                        @PathVariable("day_id") long dayId) {

        return daySummaryService.createDaySummary(scheduleId, dayId);
    }

    // get particular DaySummary
    @GetMapping("/schedules/{schedule_id}/day_summary/{day_summary_id}")
    @ResponseStatus(HttpStatus.OK)
    public DaySummary getDaySummary(@PathVariable("schedule_id") long scheduleId,
                                    @PathVariable("day_summary_id") long daySummaryId) {

        return daySummaryService.getDaySummary(scheduleId, daySummaryId);
    }
}
