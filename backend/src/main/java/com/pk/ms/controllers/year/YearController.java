package com.pk.ms.controllers.year;

import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.services.year.YearService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class YearController {

    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    @GetMapping("/years")
    public ResponseEntity<List<YearBasicInfoDTO>> getYears(@PathVariable("schedule_id") long scheduleId) {

        return ResponseEntity.ok(yearService.getAllYearsDTOs());
    }

    @GetMapping("/year/{year_id}")
    public ResponseEntity<YearBasicInfoDTO> getYearByYearId(@PathVariable("schedule_id") long scheduleId,
                                                            @PathVariable("year_id") long yearId) {

        return ResponseEntity.ok(yearService.getYearDTOById(yearId));
    }

    @GetMapping("/year")
    public ResponseEntity<YearBasicInfoDTO> getYearByLocalDate(@PathVariable("schedule_id") long scheduleId,
                                                               @RequestParam("local_date")
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                       LocalDate date) {

        return ResponseEntity.ok(yearService.getYearDTOByLocalDate(date));
    }

}
