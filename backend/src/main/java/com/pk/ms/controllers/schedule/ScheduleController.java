package com.pk.ms.controllers.schedule;

import com.pk.ms.dto.schedule.ScheduleHomePageDTO;
import com.pk.ms.mappers.schedule.HomePageMapService;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class ScheduleController {

    private final HomePageMapService homePageMapService;

    public ScheduleController(HomePageMapService homePageMapService, ScheduleService scheduleService) {
        this.homePageMapService = homePageMapService;
    }

    @GetMapping("/schedule/{schedule_id}/home")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
    public ResponseEntity<ScheduleHomePageDTO> getHomePage(@PathVariable("schedule_id") long scheduleId,
                                                          @RequestParam("local_date")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(homePageMapService.getHomePage(date, scheduleId));
    }
}
