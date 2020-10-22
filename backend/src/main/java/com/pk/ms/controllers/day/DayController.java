package com.pk.ms.controllers.day;

import com.pk.ms.dto.day.DayInputDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.services.day.DayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DayController {

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    // create Day
    @PostMapping(path = "/schedule/{schedule_id}/weeks/{week_id}/days", consumes = "application/json")
    public ResponseEntity<Day> createDay(@PathVariable("week_id") long weekId,
                                         @PathVariable("schedule_id") long scheduleId,
                                         @Valid @RequestBody DayInputDTO reqDayInputDTO) {
        return ResponseEntity.ok(dayService.createDay(scheduleId,weekId, reqDayInputDTO));
    }

    // get particular Day (with its plans)
    @GetMapping(value = "/days/{day_id}")
    public ResponseEntity<Day> getDay(@PathVariable("day_id") long dayId) {
        return ResponseEntity.ok(dayService.getDayById(dayId));
    }

    // delete Day
    @DeleteMapping("/days/{day_id}")
    public ResponseEntity<String> deleteDay(@PathVariable("day_id") long dayId) {
        dayService.deleteDay(dayId);
        return ResponseEntity.ok("Day deleted successfully. ");
    }


}
