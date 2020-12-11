package com.pk.ms.controllers.day;

import com.pk.ms.dto.day.DailyRoutinePlanInputDTO;
import com.pk.ms.entities.DeleteMessage;
import com.pk.ms.entities.day.DailyRoutinePlan;
import com.pk.ms.services.day.DailyRoutinePlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #userId")
@RequestMapping("/user/{user_id}")
public class DailyRoutinePlanController {

    private final DailyRoutinePlanService dailyRoutinePlanService;

    public DailyRoutinePlanController(DailyRoutinePlanService dailyRoutinePlanService) {
        this.dailyRoutinePlanService = dailyRoutinePlanService;
    }

    @GetMapping(value = "/daily_routine_plans")
    public ResponseEntity<List<DailyRoutinePlan>> getDailyRoutinePlans(@PathVariable("user_id") long userId) {
        return ResponseEntity.ok(dailyRoutinePlanService.getDailyRoutinePlans(userId));
    }

    @GetMapping(value = "/daily_routine_plans/{daily_routine_plan_id}")
    public ResponseEntity<DailyRoutinePlan> getDailyRoutinePlan(@PathVariable("user_id") long userId,
                                                                @PathVariable("daily_routine_plan_id") long dailyRoutinePlanId) {
        return ResponseEntity.ok(dailyRoutinePlanService.getDailyRoutinePlan(userId, dailyRoutinePlanId));
    }

    @PostMapping(value = "/daily_routine_plans")
    public ResponseEntity<DailyRoutinePlan> createDailyRoutinePlan(@PathVariable("user_id") long userId,
                                                                   @RequestBody DailyRoutinePlanInputDTO dto) {
        return ResponseEntity.ok(dailyRoutinePlanService.createDailyRoutinePlan(userId, dto));
    }

    @PatchMapping(value = "/daily_routine_plans/{daily_routine_plan_id}")
    public ResponseEntity<DailyRoutinePlan> updateDailyRoutinePlan(@PathVariable("user_id") long userId,
                                                                   @PathVariable("daily_routine_plan_id") long dailyRoutinePlanId,
                                                                   @RequestBody DailyRoutinePlanInputDTO dto) {
        return ResponseEntity.ok(dailyRoutinePlanService.updateDailyRoutinePlan(userId, dailyRoutinePlanId, dto));
    }

    @DeleteMapping(value = "/daily_routine_plans/{daily_routine_plan_id}")
    public ResponseEntity<DeleteMessage> deleteDailyRoutinePlan(@PathVariable("user_id") long userId,
                                                                @PathVariable("daily_routine_plan_id") long dailyRoutinePlanId) {
        return ResponseEntity.ok(new DeleteMessage(dailyRoutinePlanService.deleteDailyRoutinePlan(userId, dailyRoutinePlanId)));
    }
}
