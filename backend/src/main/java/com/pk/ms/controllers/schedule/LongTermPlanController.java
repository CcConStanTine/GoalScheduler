package com.pk.ms.controllers.schedule;

import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.services.schedule.LongTermPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LongTermPlanController {

    private final LongTermPlanService longTermPlanService;

    public LongTermPlanController(LongTermPlanService longTermPlanService) {
        this.longTermPlanService = longTermPlanService;
    }

    // create a LongTermPlan
    @PostMapping(value = "/schedule/{schedule_id}/long_term_plans", consumes = "application/json")
    public ResponseEntity<LongTermPlan> createLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                                          @Valid @RequestBody LongTermPlanInputDTO ltpInputDTO) {
        return ResponseEntity.ok(longTermPlanService.createLongTermPlan(scheduleId, ltpInputDTO));
    }

    // get all LongTermPlans from a particular Schedule
    @GetMapping("/schedule/{schedule_id}/long_term_plans")
    public ResponseEntity<List<LongTermPlan>> getLongTermPlans(@PathVariable("schedule_id") long scheduleId) {
        return ResponseEntity.ok(longTermPlanService.getLongTermPlans(scheduleId));
    }

    // update existing LongTermPlan
    @PatchMapping(value="/long_term_plans/{long_term_plan_id}", consumes = "application/json")
    public ResponseEntity<LongTermPlan> updateLongTermPlan(@PathVariable("long_term_plan_id") long ltpId,
                                                           @Valid @RequestBody LongTermPlanInputDTO ltpInputDTO) {
        return ResponseEntity.ok(longTermPlanService.updateLongTermPlan(ltpId, ltpInputDTO));

    }

    // delete existing LongTermPlan
    @DeleteMapping("/long_term_plans/{long_term_plan_id}")
    public ResponseEntity<String> deleteLongTermPlan(@PathVariable("long_term_plan_id") long ltpId) {
        longTermPlanService.deleteLongTermPlan(ltpId);
        return ResponseEntity.ok("Long-term plan was successfully deleted. ");
    }

}
