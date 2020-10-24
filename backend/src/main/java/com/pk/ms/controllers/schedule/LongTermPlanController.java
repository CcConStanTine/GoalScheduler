package com.pk.ms.controllers.schedule;

import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.services.schedule.LongTermPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class LongTermPlanController {

    private final LongTermPlanService longTermPlanService;

    public LongTermPlanController(LongTermPlanService longTermPlanService) {
        this.longTermPlanService = longTermPlanService;
    }

    // get all LongTermPlans from a particular Schedule
    @GetMapping("/schedules/{schedule_id}/long_term_plans")
    public List<LongTermPlan> getLongTermPlans(@PathVariable("schedule_id") long scheduleId) {

        return longTermPlanService.getLongTermPlans(scheduleId);
    }

    // create a LongTermPlan
    @PostMapping(value = "/schedules/{schedule_id}/long_term_plans", consumes = "application/json")
    public ResponseEntity<LongTermPlan> createLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                                          @Valid @RequestBody LongTermPlanInputDTO ltpInputDTO) {

        return ResponseEntity.ok(longTermPlanService.createLongTermPlan(scheduleId, ltpInputDTO));
    }

    // update existing LongTermPlan
    @PatchMapping(value="/schedules/{schedule_id}/long_term_plans/{long_term_plan_id}", consumes = "application/json")
    public ResponseEntity<LongTermPlan> updateLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                           @PathVariable("long_term_plan_id") long ltpId,
                                           @Valid @RequestBody LongTermPlanInputDTO ltpInputDTO) {

        return ResponseEntity.ok(longTermPlanService.updateLongTermPlan(scheduleId, ltpId, ltpInputDTO));
    }

    // change the fulfilled status
    @PatchMapping("/schedules/{schedule_id}/long_term_plans/{long_term_plan_id}/fulfilled")
    public ResponseEntity<LongTermPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                        @PathVariable("long_term_plan_id") long yearPlanId) {
        return ResponseEntity.ok(longTermPlanService.updateFulfilledStatus(scheduleId, yearPlanId));
    }

    // delete existing LongTermPlan
    @DeleteMapping("/schedules/{schedule_id}/long_term_plans/{long_term_plan_id}")
    public ResponseEntity<String> deleteLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                     @PathVariable("long_term_plan_id") long ltpId) {

        return ResponseEntity.ok(longTermPlanService.deleteLongTermPlan(scheduleId, ltpId));
    }
}
