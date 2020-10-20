package com.pk.ms.controllers.year;

import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.services.year.YearPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class YearPlanController {

    private final YearPlanService yearPlanService;

    public YearPlanController(YearPlanService yearPlanService) {
        this.yearPlanService = yearPlanService;
    }

    // create a new YearPlan by passing content, startDate and endDate
    @PostMapping(value="/years/{year_id}/year_plans", consumes = "application/json")
    public ResponseEntity<YearPlan> createYearPlan(@PathVariable("year_id") long yearId, @Valid @RequestBody YearPlanInputDTO yearPlanInputDTO) {
        return ResponseEntity.ok(yearPlanService.createYearPlan(yearId, yearPlanInputDTO));
    }

    // update existing YearPlan
    @PatchMapping(value="/year_plans/{year_plan_id}", consumes = "application/json")
    public ResponseEntity<YearPlan> updateYearPlan(@PathVariable("year_plan_id") long yearPlanId, @Valid @RequestBody YearPlanInputDTO yearPlanInputDTO) {
        return ResponseEntity.ok(yearPlanService.updateYearPlan(yearPlanId, yearPlanInputDTO));
    }

    // delete existing YearPlan
    @DeleteMapping("/year_plans/{year_plan_id}")
    public ResponseEntity<String> deleteYearPlan(@PathVariable("year_plan_id") long yearPlanId) {
        yearPlanService.deleteYearPlan(yearPlanId);
        return ResponseEntity.ok("Year plan was successfully deleted. ");
    }

    // change the fulfilled status
    @PatchMapping("/year_plans/{year_plan_id}/fulfilled")
    public ResponseEntity<String> updateFulfilledStatus(@PathVariable("year_plan_id") long yearPlanId) {
        yearPlanService.updateFulfilledStatus(yearPlanId);
        return ResponseEntity.ok("Changed successfully. ");
    }


}
