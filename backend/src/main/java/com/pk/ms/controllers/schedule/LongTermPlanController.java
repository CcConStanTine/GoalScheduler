package com.pk.ms.controllers.schedule;

import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.DeleteMessage;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.services.schedule.LongTermPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class LongTermPlanController {

    private final LongTermPlanService longTermPlanService;

    public LongTermPlanController(LongTermPlanService longTermPlanService) {
        this.longTermPlanService = longTermPlanService;
    }

    @GetMapping("/long_term_plans")
    public List<LongTermPlan> getLongTermPlans(@PathVariable("schedule_id") long scheduleId) {

        return longTermPlanService.getLongTermPlansByScheduleId(scheduleId);
    }

    @PostMapping(value = "/long_term_plan", consumes = "application/json")
    public ResponseEntity<LongTermPlan> createLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                                           @Valid @RequestBody LongTermPlanInputDTO ltpInputDTO) {

        return ResponseEntity.ok(longTermPlanService.createLongTermPlan(scheduleId, ltpInputDTO));
    }

    @PatchMapping(value="/long_term_plan/{long_term_plan_id}", consumes = "application/json")
    public ResponseEntity<LongTermPlan> updateLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                                           @PathVariable("long_term_plan_id") long ltpId,
                                                           @Valid @RequestBody LongTermPlanInputDTO ltpInputDTO) {

        return ResponseEntity.ok(longTermPlanService.updateLongTermPlan(scheduleId, ltpId, ltpInputDTO));
    }

    @PatchMapping("/long_term_plan/{long_term_plan_id}/fulfilled")
    public ResponseEntity<LongTermPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                              @PathVariable("long_term_plan_id") long ltpId) {

        return ResponseEntity.ok(longTermPlanService.updateFulfilledStatus(scheduleId, ltpId));
    }

    @DeleteMapping("/long_term_plan/{long_term_plan_id}")
    public ResponseEntity<DeleteMessage> deleteLongTermPlan(@PathVariable("schedule_id") long scheduleId,
                                                            @PathVariable("long_term_plan_id") long ltpId) {

        return ResponseEntity.ok(new DeleteMessage(longTermPlanService.deleteLongTermPlan(scheduleId, ltpId)));
    }
}