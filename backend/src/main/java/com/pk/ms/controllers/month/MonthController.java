package com.pk.ms.controllers.month;

import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.services.month.MonthService;
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
public class MonthController {

    private final MonthService monthService;

    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    @GetMapping("/year/{year_id}/months")
    public ResponseEntity<List<MonthBasicInfoDTO>> getMonthsByYearId(@PathVariable("schedule_id") long scheduleId,
                                                                     @PathVariable("year_id") long year_id) {

        return ResponseEntity.ok(monthService.getMonthDTOsByYearId(year_id));
    }

    @GetMapping("/months")
    public ResponseEntity<List<MonthBasicInfoDTO>> getMonthsByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                                        @RequestParam("local_date")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                LocalDate date) {

        return ResponseEntity.ok(monthService.getMonthDTOsByLocalDate(date));
    }

    @GetMapping("/month/{month_id}")
    public ResponseEntity<MonthBasicInfoDTO> getMonthByMonthId(@PathVariable("schedule_id") long scheduleId,
                                                               @PathVariable("month_id") long month_id) {

        return ResponseEntity.ok(monthService.getMonthDTOById(month_id));
    }

    @GetMapping("/month")
    public ResponseEntity<MonthBasicInfoDTO> getMonthByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                                 @RequestParam("local_date")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                         LocalDate date) {

        return ResponseEntity.ok(monthService.getMonthDTOByLocalDate(date));
    }


}