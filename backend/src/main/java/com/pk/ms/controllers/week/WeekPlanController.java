package com.pk.ms.controllers.week;

import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.services.week.WeekPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class WeekPlanController {

    private final WeekPlanService weekPlanService;

    public WeekPlanController(WeekPlanService weekPlanService) {
        this.weekPlanService = weekPlanService;
    }

    @GetMapping(value = "/week/{week_id}/week_plans")
    public ResponseEntity<List<WeekPlan>> getWeekPlans(@PathVariable("schedule_id") long scheduleId,
                                                       @PathVariable("week_id") long weekId) {
        return ResponseEntity.ok(weekPlanService.getWeekPlansByScheduleIdAndWeekId(scheduleId, weekId));
    }

    @GetMapping(value = "/week_plans/{week_plan_id}")
    public ResponseEntity<WeekPlan> getWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                                @PathVariable("week_plan_id") long weekPlanId) {
        return ResponseEntity.ok(weekPlanService.getWeekPlan(scheduleId, weekPlanId));
    }

    @PostMapping(value="/week/{week_id}/week_plan", consumes = "application/json")
    public ResponseEntity<WeekPlan> createWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                                   @PathVariable("week_id") long weekId,
                                                   @Valid @RequestBody WeekPlanInputDTO weekPlanInputDTO) {

        return ResponseEntity.ok(weekPlanService.createWeekPlan(scheduleId, weekId, weekPlanInputDTO));
    }

    @PatchMapping(value="/week_plan/{week_plan_id}", consumes = "application/json")
    public ResponseEntity<WeekPlan> updateWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                                   @PathVariable("week_plan_id") long weekPlanId,
                                                   @Valid @RequestBody WeekPlanInputDTO weekPlanInputDTO) {

        return ResponseEntity.ok(weekPlanService.updateWeekPlan(scheduleId, weekPlanId, weekPlanInputDTO));
    }

    @PatchMapping("/week_plan/{week_plan_id}/fulfilled")
    public ResponseEntity<WeekPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                          @PathVariable("week_plan_id") long weekPlanId) {

        return ResponseEntity.ok(weekPlanService.updateFulfilledStatus(scheduleId, weekPlanId));
    }

    @DeleteMapping("/week_plan/{week_plan_id}")
    public ResponseEntity<String> deleteWeekPlan(@PathVariable("schedule_id") long scheduleId,
                                                 @PathVariable("week_plan_id") long weekPlanId) {

        return ResponseEntity.ok(weekPlanService.deleteWeekPlan(scheduleId, weekPlanId));
    }

}