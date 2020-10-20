package com.pk.ms.controllers.year;

import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.dto.year.YearInputDTO;
import com.pk.ms.dto.year.YearWithBasicMonthAndWeekDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.services.year.YearService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class YearController {


    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    // create a new Year by passing the yearNumber (the logic will do the rest) and the id of Schedule (to path)
    @PostMapping(value="/schedule/{schedule_id}/years", consumes = "application/json")
    public ResponseEntity<Year> createYear(@PathVariable("schedule_id") long scheduleId, @Valid @RequestBody YearInputDTO yearInputDTO) {
        return ResponseEntity.ok(yearService.createYear(scheduleId, yearInputDTO));
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get all Years which belongs to particular schedule
    @GetMapping("/schedule/{schedule_id}/years")
    public ResponseEntity<List<YearBasicInfoDTO>> getYears(@PathVariable("schedule_id") long scheduleId) {
        return ResponseEntity.ok(yearService.getYears(scheduleId));
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get particular Year with its plans List of MonthBasicInfoDTO
    @GetMapping("/years/{year_id}")
    public ResponseEntity<YearWithBasicMonthAndWeekDTO> getYear(@PathVariable("year_id") long yearId) {
        return ResponseEntity.ok(yearService.getYear(yearId));
    }

    // delete a Year by passing the yearId as a PathVar
    @DeleteMapping("/years/{year_id}")
    public ResponseEntity<String> deleteYear(@PathVariable("year_id") long yearId) {
        yearService.deleteYear(yearId);
        return ResponseEntity.ok("Year was successfully deleted. ");
    }

}
