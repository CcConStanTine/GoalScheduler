package com.pk.ms.controllers.day;

import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.services.day.DayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class DayController {

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping("/month/{month_id}/days")
    public ResponseEntity<List<DayBasicInfoDTO>> getDaysByMonthId(@PathVariable("schedule_id") long scheduleId,
                                                                  @PathVariable("month_id") long monthId) {

        return ResponseEntity.ok(dayService.getDayDTOsByMonthId(monthId));
    }

    @GetMapping("/days")
    public ResponseEntity<List<DayBasicInfoDTO>> getDaysByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                                    @RequestParam("local_date")
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                            LocalDate date) {

        return ResponseEntity.ok(dayService.getDayDTOsByLocalDate(date));
    }

    @GetMapping("/day/{day_id}")
    public ResponseEntity<DayBasicInfoDTO> getDayByDayId(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("day_id") long dayId) {

        return ResponseEntity.ok(dayService.getDayDTOByDayId(dayId));
    }

    @GetMapping("/day")
    public ResponseEntity<DayBasicInfoDTO> getDayByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                             @RequestParam("local_date")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                     LocalDate date) {

        return ResponseEntity.ok(dayService.getDayDTOByLocalDate(date));
    }

}
