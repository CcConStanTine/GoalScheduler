package com.pk.ms.controllers.year;

import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.dto.year.YearInputDTO;
import com.pk.ms.dto.year.YearWithBasicMonthAndWeekDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.services.year.YearService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class YearController {

    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get all Years which belongs to particular schedule
    @GetMapping("/schedules/{schedule_id}/years")
    public ResponseEntity<List<YearBasicInfoDTO>> getYears(@PathVariable("schedule_id") long scheduleId) {

        return ResponseEntity.ok(yearService.getYears(scheduleId));
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get particular Year with its plans List of MonthBasicInfoDTO
    @GetMapping("/schedules/{schedule_id}/years/{year_id}")
    public ResponseEntity<YearWithBasicMonthAndWeekDTO> getYear(@PathVariable("schedule_id") long scheduleId,
                                                @PathVariable("year_id") long yearId) {

        return ResponseEntity.ok(yearService.getYear(scheduleId, yearId));
    }

    // create a new Year by passing the yearNumber (the logic will do the rest) and the id of Schedule (to path)
    @PostMapping(value="/schedules/{schedule_id}/years", consumes = "application/json")
    public ResponseEntity<Year> createYear(@PathVariable("schedule_id") long scheduleId,
                           @Valid @RequestBody YearInputDTO yearInputDTO) {

        return ResponseEntity.ok(yearService.createYear(scheduleId, yearInputDTO));
    }

    // delete a Year by passing the yearId as a PathVar
    @DeleteMapping("/schedules/{schedule_id}/years/{year_id}")
    public ResponseEntity<String> deleteYear(@PathVariable("schedule_id") long scheduleId,
                           @PathVariable("year_id") long yearId) {
        return ResponseEntity.ok(yearService.deleteYear(scheduleId, yearId));
    }

}
