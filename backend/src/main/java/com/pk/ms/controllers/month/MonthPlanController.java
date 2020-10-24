package com.pk.ms.controllers.month;

import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.services.month.MonthPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class MonthPlanController {

    private final MonthPlanService monthPlanService;

    public MonthPlanController(MonthPlanService monthPlanService) {
        this.monthPlanService = monthPlanService;
    }

    // create a new MonthPlan by passing content, startDate and endDate
    @PostMapping(value="/schedules/{schedule_id}/months/{month_id}/month_plans", consumes = "application/json")
    public ResponseEntity<MonthPlan> createYearPlan(@PathVariable("schedule_id") long scheduleId,
                                                    @PathVariable("month_id") long monthId,
                                                    @Valid @RequestBody MonthPlanInputDTO monthPlanInputDTO) {

        return ResponseEntity.ok(monthPlanService.createMonthPlan(scheduleId, monthId, monthPlanInputDTO));
    }

    // update existing MonthPlan
    @PatchMapping(value="/schedules/{schedule_id}/month_plans/{month_plan_id}", consumes = "application/json")
    public ResponseEntity<MonthPlan> updateMonthPlan(@PathVariable("schedule_id") long scheduleId,
                                                     @PathVariable("month_plan_id") long monthPlanId,
                                                     @Valid @RequestBody MonthPlanInputDTO monthPlanInputDTO) {

        return ResponseEntity.ok(monthPlanService.updateMonthPlan(scheduleId, monthPlanId, monthPlanInputDTO));
    }

    // change the fulfilled status
    @PatchMapping("/schedules/{schedule_id}/month_plans/{month_plan_id}/fulfilled")
    public ResponseEntity<MonthPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                       @PathVariable("month_plan_id") long monthPlanId) {

        return ResponseEntity.ok(monthPlanService.updateFulfilledStatus(scheduleId, monthPlanId));
    }

    // delete existing MonthPlan
    @DeleteMapping("/schedules/{schedule_id}/month_plans/{month_plan_id}")
    public ResponseEntity<String> deleteMonthPlan(@PathVariable("schedule_id") long scheduleId,
                                                  @PathVariable("month_plan_id") long monthPlanId) {

        return ResponseEntity.ok(monthPlanService.deleteMonthPlan(scheduleId, monthPlanId));
    }

}
