package com.pk.ms.controllers.week;

import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.services.week.WeekPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class WeekPlanController {

    private final WeekPlanService weekPlanService;

    public WeekPlanController(WeekPlanService weekPlanService) {
        this.weekPlanService = weekPlanService;
    }

    @PostMapping(path = "/schedules/{schedule_id}/weeks/{week_id}/week_plans", consumes = "application/json")
    public ResponseEntity<WeekPlan> createWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                           @PathVariable("week_id") long weekId,
                                           @Valid @RequestBody WeekPlanInputDTO weekPlanInputDTO) {

        return ResponseEntity.ok(weekPlanService.createWeekPlan(scheduleId, weekId, weekPlanInputDTO));
    }

    @PatchMapping(value="/schedules/{schedule_id}/week_plans/{week_plan_id}", consumes = "application/json")
    public ResponseEntity<WeekPlan> updateWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                                   @PathVariable("week_plan_id") long weekPlanId,
                                                   @Valid @RequestBody WeekPlanInputDTO weekPlanInputDTO) {

        return ResponseEntity.ok(weekPlanService.updateWeekPlan(scheduleId, weekPlanId, weekPlanInputDTO));
    }

    @PatchMapping("/schedules/{schedule_id}/week_plans/{week_plan_id}/fulfilled")
    public ResponseEntity<WeekPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                          @PathVariable("week_plan_id") long weekPlanId) {

        return ResponseEntity.ok(weekPlanService.updateFulfilledStatus(scheduleId, weekPlanId));
    }

    @DeleteMapping("/schedules/{schedule_id}/week_plans/{week_plan_id}")
    public ResponseEntity<String> deleteWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                                 @PathVariable("week_plan_id") long weekPlanId) {

        return ResponseEntity.ok(weekPlanService.deleteWeekPlan(scheduleId, weekPlanId));
    }
}
