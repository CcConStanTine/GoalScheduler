package com.pk.ms.controllers.year;

import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.services.year.YearPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class YearPlanController {

    private final YearPlanService yearPlanService;

    public YearPlanController(YearPlanService yearPlanService) {
        this.yearPlanService = yearPlanService;
    }

    @PostMapping(value="/schedules/{schedule_id}/years/{year_id}/year_plans", consumes = "application/json")
    public ResponseEntity<YearPlan> createYearPlan(@PathVariable("schedule_id") long scheduleId,
                                                   @PathVariable("year_id") long yearId,
                                                   @Valid @RequestBody YearPlanInputDTO yearPlanInputDTO) {

        return ResponseEntity.ok(yearPlanService.createYearPlan(scheduleId, yearId, yearPlanInputDTO));
    }

    @PatchMapping(value="/schedules/{schedule_id}/year_plans/{year_plan_id}", consumes = "application/json")
    public ResponseEntity<YearPlan> updateYearPlan(@PathVariable("schedule_id") long scheduleId,
                                                   @PathVariable("year_plan_id") long yearPlanId,
                                                   @Valid @RequestBody YearPlanInputDTO yearPlanInputDTO) {

        return ResponseEntity.ok(yearPlanService.updateYearPlan(scheduleId, yearPlanId, yearPlanInputDTO));
    }

    @PatchMapping("/schedules/{schedule_id}/year_plans/{year_plan_id}/fulfilled")
    public ResponseEntity<YearPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                          @PathVariable("year_plan_id") long yearPlanId) {

        return ResponseEntity.ok(yearPlanService.updateFulfilledStatus(scheduleId, yearPlanId));
    }

    @DeleteMapping("/schedules/{schedule_id}/year_plans/{year_plan_id}")
    public ResponseEntity<String> deleteYearPlan(@PathVariable("schedule_id") long scheduleId,
                                                 @PathVariable("year_plan_id") long yearPlanId) {

        return ResponseEntity.ok(yearPlanService.deleteYearPlan(scheduleId, yearPlanId));
    }
}
