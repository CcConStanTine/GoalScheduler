package com.pk.ms.controllers.schedule;

import com.pk.ms.dto.schedule.ScheduleHomePageDTO;
import com.pk.ms.mappers.schedule.HomePageMapService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class ScheduleController {

    private final HomePageMapService homePageMapService;

    public ScheduleController(HomePageMapService homePageMapService) {
        this.homePageMapService = homePageMapService;
    }

    // ONE OF THE FINAL ENDPOINTS
    // get a Home Page based on local date took from user system
    @GetMapping("/schedule/{schedule_id}/home")
    public ResponseEntity<ScheduleHomePageDTO> getHomePage(@PathVariable("schedule_id") long scheduleId,
                                                          @RequestParam("local_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(homePageMapService.getHomePage(date, scheduleId));
    }

}
