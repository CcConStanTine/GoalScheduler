package com.pk.ms.controllers.month;

import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.services.month.MonthPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MonthPlanController {

    private final MonthPlanService monthPlanService;

    public MonthPlanController(MonthPlanService monthPlanService) {
        this.monthPlanService = monthPlanService;
    }

    // create a new MonthPlan by passing content, startDate and endDate
    @PostMapping(value="/months/{month_id}/month_plans", consumes = "application/json")
    public ResponseEntity<MonthPlan> createYearPlan(@PathVariable("month_id") long monthId, @Valid @RequestBody MonthPlanInputDTO monthPlanInputDTO) {
        return ResponseEntity.ok(monthPlanService.createYearPlan(monthId, monthPlanInputDTO));
    }

    // update existing MonthPlan
    @PatchMapping(value="/month_plans/{month_plan_id}", consumes = "application/json")
    public ResponseEntity<MonthPlan> updateMonthPlan(@PathVariable("month_plan_id") long monthPlanId, @Valid @RequestBody MonthPlanInputDTO monthPlanInputDTO) {
        return ResponseEntity.ok(monthPlanService.updateMonthPlan(monthPlanId, monthPlanInputDTO));
    }

    // delete existing MonthPlan
    @DeleteMapping("/month_plans/{month_plan_id}")
    public ResponseEntity<String> deleteMonthPlan(@PathVariable("month_plan_id") long monthPlanId) {
        monthPlanService.deleteMonthPlan(monthPlanId);
        return ResponseEntity.ok("Month plan successfully deleted. ");
    }

    // change the fulfilled status
    @PatchMapping("/month_plans/{month_plan_id}/fulfilled")
    public ResponseEntity<String> updateFulfilledStatus(@PathVariable("month_plan_id") long monthPlanId) {
        monthPlanService.updateFulfilledStatus(monthPlanId);
        return ResponseEntity.ok("Changed successfully. ");
    }


}
