package com.pk.ms.controllers.week;

import com.pk.ms.dto.week.WeekInputDTO;
import com.pk.ms.dto.week.WeekWithBasicDayDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.services.week.WeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class WeekController {

    private final WeekService weekService;

    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    // create Week
    @PostMapping(path = "/years/{year_id}/weeks", consumes = "application/json")
    public ResponseEntity<Week> createWeek(@PathVariable("year_id") long yearId, @Valid @RequestBody WeekInputDTO weekInputDTO) {
        return ResponseEntity.ok(weekService.createWeek(yearId, weekInputDTO));
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get particular Week with its plans and list of DayBasicInfoDTO
    @GetMapping("/weeks/{week_id}")
    public ResponseEntity<WeekWithBasicDayDTO> getWeek(@PathVariable("week_id") long weekId) {
        return ResponseEntity.ok(weekService.getWeek(weekId));
    }

    // delete Week
    @DeleteMapping("/weeks/{week_id}")
    public ResponseEntity<String> deleteWeek(@PathVariable("week_id") long weekId) {
        weekService.deleteWeek(weekId);
        return ResponseEntity.ok("Week successfully deleted. ");
    }

}
