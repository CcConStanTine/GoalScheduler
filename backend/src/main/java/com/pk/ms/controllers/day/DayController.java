package com.pk.ms.controllers.day;

import com.pk.ms.dto.day.DayInputDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.services.day.DayService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class DayController {

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    // create Day
    @PostMapping(path = "/schedules/{schedule_id}/weeks/{week_id}/days", consumes = "application/json")
    public ResponseEntity<Day> createDay(@PathVariable("schedule_id") long scheduleId,
                                         @PathVariable("week_id") long weekId,
                                         @Valid @RequestBody DayInputDTO reqDayInputDTO) {
        return ResponseEntity.ok(dayService.createDay(scheduleId,weekId, reqDayInputDTO));
    }

    // get particular Day (with its plans)
    @GetMapping(value = "/schedules/{schedule_id}/days/{day_id}")
    public ResponseEntity<Day> getDay(@PathVariable("schedule_id") long scheduleId,
                                      @PathVariable("day_id") long dayId) {
        return ResponseEntity.ok(dayService.getDay(scheduleId, dayId));
    }

    // delete Day
    @DeleteMapping("/schedules/{schedule_id}/days/{day_id}")
    public ResponseEntity<String> deleteDay(@PathVariable("schedule_id") long scheduleId,
                                            @PathVariable("day_id") long dayId) {
        return ResponseEntity.ok(dayService.deleteDay(scheduleId, dayId));
    }


}
