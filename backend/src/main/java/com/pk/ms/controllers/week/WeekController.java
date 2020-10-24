package com.pk.ms.controllers.week;

import com.pk.ms.dto.week.WeekInputDTO;
import com.pk.ms.dto.week.WeekWithBasicDayDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.services.week.WeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class WeekController {

    private final WeekService weekService;

    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    // create Week
    @PostMapping(path = "/schedules/{schedule_id}/years/{year_id}/weeks", consumes = "application/json")
    public ResponseEntity<Week> createWeek(@PathVariable("schedule_id") long scheduleId,
                                           @PathVariable("year_id") long yearId,
                                           @Valid @RequestBody WeekInputDTO weekInputDTO) {

        return ResponseEntity.ok(weekService.createWeek(scheduleId, yearId, weekInputDTO));
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get particular Week with its plans and list of DayBasicInfoDTO
    @GetMapping("/schedules/{schedule_id}/weeks/{week_id}")
    public ResponseEntity<WeekWithBasicDayDTO> getWeek(@PathVariable("schedule_id") long scheduleId,
                                                       @PathVariable("week_id") long weekId) {

        return ResponseEntity.ok(weekService.getWeek(scheduleId, weekId));
    }

    // delete Week
    @DeleteMapping("/schedules/{schedule_id}/weeks/{week_id}")
    public ResponseEntity<String> deleteWeek(@PathVariable("schedule_id") long scheduleId,
                                             @PathVariable("week_id") long weekId) {

        return ResponseEntity.ok(weekService.deleteWeek(scheduleId, weekId));
    }

}
