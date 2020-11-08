package com.pk.ms.controllers.day;

import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.services.day.DaySummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class DaySummaryController {

    private final DaySummaryService daySummaryService;

    public DaySummaryController(DaySummaryService daySummaryService) {
        this.daySummaryService = daySummaryService;
    }

    @GetMapping("/day_summary/{day_summary_id}")
    public ResponseEntity<DaySummary> getDaySummaryById(@PathVariable("schedule_id") long scheduleId,
                                                        @PathVariable("day_summary_id") long daySummaryId) {

        return ResponseEntity.ok(daySummaryService.getDaySummaryById(scheduleId, daySummaryId));
    }

    @GetMapping("/day/{day_id}/day_summary")
    public ResponseEntity<DaySummary> getDaySummaryByDayId(@PathVariable("schedule_id") long scheduleId,
                                                           @PathVariable("day_id") long dayId) {

        return ResponseEntity.ok(daySummaryService.getDaySummaryByScheduleIdAndDayId(scheduleId, dayId));
    }

    @PostMapping("/day/{day_id}/day_summary")
    public ResponseEntity<DaySummary> createDaySummary(@PathVariable("schedule_id") long scheduleId,
                                                       @PathVariable("day_id") long dayId) {

        return ResponseEntity.ok(daySummaryService.createDaySummary(scheduleId, dayId));
    }

}
