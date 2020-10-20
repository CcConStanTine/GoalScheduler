package com.pk.ms.controllers.day;

import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.services.day.DayPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DayPlanController {


    private final DayPlanService dayPlanService;

    public DayPlanController(DayPlanService dayPlanService) {
        this.dayPlanService = dayPlanService;
    }

    // create DayPlan
    @PostMapping(path = "/days/{day_id}/day_plans", consumes = "application/json")
    public ResponseEntity<DayPlan> createDayPlan(@PathVariable("day_id") long dayId, @Valid @RequestBody DayPlanInputDTO dayPlanInputDTO) {
        return ResponseEntity.ok(dayPlanService.createDayPlan(dayId, dayPlanInputDTO));
    }

    // update existing DayPlan
    @PatchMapping(value="/day_plans/{day_plan_id}", consumes = "application/json")
    public ResponseEntity<DayPlan> updateDayPlan(@PathVariable("day_plan_id") long dayPlanId, @Valid @RequestBody DayPlanInputDTO dayPlanInputDTO) {
        return ResponseEntity.ok(dayPlanService.updateDayPlan(dayPlanId, dayPlanInputDTO));
    }

    // delete existing YearPlan
    @DeleteMapping("/day_plans/{day_plan_id}")
    public ResponseEntity<String> deleteDayPlan(@PathVariable("day_plan_id") long dayPlanId) {
        dayPlanService.deleteDayPlan(dayPlanId);
        return ResponseEntity.ok("Year plan deleted successfully. ");
    }

    // change the fulfilled status
    @PatchMapping("/day_plans/{day_plan_id}/fulfilled")
    public ResponseEntity<String> updateFulfilledStatus(@PathVariable("day_plan_id") long dayPlanId) {
        dayPlanService.updateFulfilledStatus(dayPlanId);
        return ResponseEntity.ok("Changed successfully. ");
    }

}
