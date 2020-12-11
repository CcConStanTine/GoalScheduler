package com.pk.ms.controllers.month;

import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.DeleteMessage;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.services.month.MonthPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
@RequestMapping("/schedule/{schedule_id}")
public class MonthPlanController {

    private final MonthPlanService monthPlanService;

    public MonthPlanController(MonthPlanService monthPlanService) {
        this.monthPlanService = monthPlanService;
    }

    @GetMapping(value = "/month/{month_id}/month_plans")
    public ResponseEntity<List<MonthPlan>> getMonthPlans(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("month_id") long monthId) {
        return ResponseEntity.ok(monthPlanService.getMonthPlansByScheduleIdAndMonthId(scheduleId, monthId));
    }

    @GetMapping(value = "/month_plans/{month_plan_id}")
    public ResponseEntity<MonthPlan> getMonthPlan(@PathVariable("schedule_id") long scheduleId,
                                                  @PathVariable("month_plan_id") long monthPlanId) {
        return ResponseEntity.ok(monthPlanService.getMonthPlan(scheduleId, monthPlanId));
    }

    @PostMapping(value="/month/{month_id}/month_plan", consumes = "application/json")
    public ResponseEntity<MonthPlan> createMonthPlan(@PathVariable("schedule_id") long scheduleId,
                                                     @PathVariable("month_id") long monthId,
                                                     @Valid @RequestBody MonthPlanInputDTO monthPlanInputDTO) {

        return ResponseEntity.ok(monthPlanService.createMonthPlan(scheduleId, monthId, monthPlanInputDTO));
    }

    @PatchMapping(value="/month_plan/{month_plan_id}", consumes = "application/json")
    public ResponseEntity<MonthPlan> updateMonthPlan(@PathVariable("schedule_id") long scheduleId,
                                                     @PathVariable("month_plan_id") long monthPlanId,
                                                     @Valid @RequestBody MonthPlanInputDTO monthPlanInputDTO) {

        return ResponseEntity.ok(monthPlanService.updateMonthPlan(scheduleId, monthPlanId, monthPlanInputDTO));
    }

    @PatchMapping("/month_plan/{month_plan_id}/fulfilled")
    public ResponseEntity<MonthPlan> updateFulfilledStatus(@PathVariable("schedule_id") long scheduleId,
                                                           @PathVariable("month_plan_id") long monthPlanId) {

        return ResponseEntity.ok(monthPlanService.updateFulfilledStatus(scheduleId, monthPlanId));
    }

    @DeleteMapping("/month_plan/{month_plan_id}")
    public ResponseEntity<DeleteMessage> deleteMonthPlan(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("month_plan_id") long monthPlanId) {

        return ResponseEntity.ok(new DeleteMessage(monthPlanService.deleteMonthPlan(scheduleId, monthPlanId)));
    }


}
