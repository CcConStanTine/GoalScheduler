package com.pk.ms.controllers.day;

import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.services.day.DayPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class DayPlanController {

    private final DayPlanService dayPlanService;

    public DayPlanController(DayPlanService dayPlanService) {
        this.dayPlanService = dayPlanService;
    }

    @GetMapping(value = "/day/{day_id}/day_plans")
    public ResponseEntity<List<DayPlan>> getDayPlans(@PathVariable("schedule_id") long scheduleId,
                                                     @PathVariable("day_id") long dayId) {
        return ResponseEntity.ok(dayPlanService.getDayPlansByScheduleIdAndDayId(scheduleId, dayId));
    }

    @PostMapping(value="/day/{day_id}/day_plans", consumes = "application/json")
    public ResponseEntity<DayPlan> createDayPlan(@PathVariable("schedule_id") long scheduleId,
                                                 @PathVariable("day_id") long dayId,
                                                 @Valid @RequestBody DayPlanInputDTO dayPlanInputDTO) {

        return ResponseEntity.ok(dayPlanService.createDayPlan(scheduleId, dayId, dayPlanInputDTO));
    }

    @PatchMapping(value="/day_plan/{day_plan_id}", consumes = "application/json")
    public ResponseEntity<DayPlan> updateDayPlan(@PathVariable("schedule_id") long scheduleId,
                                                 @PathVariable("day_plan_id") long dayPlanId,
                                                 @Valid @RequestBody DayPlanInputDTO dayPlanInputDTO) {

        return ResponseEntity.ok(dayPlanService.updateDayPlan(scheduleId, dayPlanId, dayPlanInputDTO));
    }

    @PatchMapping("/day_plan/{day_plan_id}/fulfilled")
    public ResponseEntity<DayPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("day_plan_id") long dayPlanId) {

        return ResponseEntity.ok(dayPlanService.updateFulfilledStatus(scheduleId, dayPlanId));
    }

    @DeleteMapping("/day_plan/{day_plan_id}")
    public ResponseEntity<String> deleteDayPlan(@PathVariable("schedule_id") long scheduleId,
                                                @PathVariable("day_plan_id") long dayPlanId) {

        return ResponseEntity.ok(dayPlanService.deleteDayPlan(scheduleId, dayPlanId));
    }
}
