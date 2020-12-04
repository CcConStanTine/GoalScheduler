package com.pk.ms.controllers.week;

import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.services.week.WeekService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class WeekController {

    private final WeekService weekService;

    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    @GetMapping("/year/{year_id}/weeks")
    public ResponseEntity<List<WeekBasicInfoDTO>> getWeeksByYearId(@PathVariable("schedule_id") long scheduleId,
                                                                   @PathVariable("year_id") long yearId) {

        return ResponseEntity.ok(weekService.getWeekDTOsByYearId(yearId));
    }

    @GetMapping("/weeks")
    public ResponseEntity<List<WeekBasicInfoDTO>> getWeeksByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                                      @RequestParam("local_date")
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                              LocalDate date) {

        return ResponseEntity.ok(weekService.getWeekDTOsByLocalDate(date));
    }

    @GetMapping("/week/{week_id}")
    public ResponseEntity<WeekBasicInfoDTO> getWeekByWeekId(@PathVariable("schedule_id") long scheduleId,
                                                            @PathVariable("week_id") long weekId) {

        return ResponseEntity.ok(weekService.getWeekDTOByWeekId(weekId));
    }

    @GetMapping("/week")
    public ResponseEntity<WeekBasicInfoDTO> getWeekByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                               @RequestParam("local_date")
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                       LocalDate date) {

        return ResponseEntity.ok(weekService.getWeekDTOByLocalDate(date));
    }
}
