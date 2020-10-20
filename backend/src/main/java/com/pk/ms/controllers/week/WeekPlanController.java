package com.pk.ms.controllers.week;

import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.services.week.WeekPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class WeekPlanController {

    private final WeekPlanService weekPlanService;

    public WeekPlanController(WeekPlanService weekPlanService) {
        this.weekPlanService = weekPlanService;
    }

    // create WeekPlan
    @PostMapping(path = "/weeks/{week_id}/week_plans", consumes = "application/json")
    public ResponseEntity<WeekPlan> create(@PathVariable("week_id") long weekId, @Valid @RequestBody WeekPlanInputDTO weekPlanInputDTO) {
        return ResponseEntity.ok(weekPlanService.createWeekPlan(weekId, weekPlanInputDTO));
    }

    // update existing WeekPlan
    @PatchMapping(value="/week_plans/{week_plan_id}", consumes = "application/json")
    public ResponseEntity<WeekPlan> updateWeekPlan(@PathVariable("week_plan_id") long weekPlanId, @Valid @RequestBody WeekPlanInputDTO weekPlanInputDTO) {
        return ResponseEntity.ok(weekPlanService.updateWeekPlan(weekPlanId, weekPlanInputDTO));
    }

    // delete existing WeekPlan
    @DeleteMapping("/week_plans/{week_plan_id}")
    public ResponseEntity<String> deleteWeekPlan(@PathVariable("week_plan_id") long weekPlanId) {
        weekPlanService.deleteWeekPlan(weekPlanId);
        return ResponseEntity.ok("Week plan successfully deleted. ");
    }

    // change the fulfilled status
    @PatchMapping("/week_plans/{week_plan_id}/fulfilled")
    public ResponseEntity<String> updateFulfilledStatus(@PathVariable("week_plan_id") long weekPlanId) {
        weekPlanService.updateFulfilledStatus(weekPlanId);
        return ResponseEntity.ok("Changed successfully. ");
    }

}
