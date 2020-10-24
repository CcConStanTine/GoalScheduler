package com.pk.ms.controllers.month;

import com.pk.ms.dto.month.MonthInputDTO;
import com.pk.ms.dto.month.MonthWithBasicDayDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.services.month.MonthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #scheduleId")
public class MonthController {

    private final MonthService monthService;

    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    // create a Month
    @PostMapping(value = "/schedules/{schedule_id}/years/{year_id}/months",consumes = "application/json")
    public ResponseEntity<Month> createMonth(@PathVariable("schedule_id") long scheduleId,
                                             @PathVariable("year_id") long yearId,
                                             @RequestBody MonthInputDTO monthInputDTO) {
        return ResponseEntity.ok(monthService.createMonth(scheduleId, yearId, monthInputDTO));
    }

    // ONE OF THE FINAL ENDPOINTS!!!
    // get particular Month with its plans and list of DayBasicInfoDTO
    @GetMapping("/schedules/{schedule_id}/months/{month_id}")
    public ResponseEntity<MonthWithBasicDayDTO> getMonth(@PathVariable("schedule_id") long scheduleId,
                                                         @PathVariable("month_id") long monthId) {
        return ResponseEntity.ok(monthService.getMonth(scheduleId, monthId));
    }

    // delete Month
    @DeleteMapping("/schedules/{schedule_id}/months/{month_id}")
    public ResponseEntity<String> deleteMonth(@PathVariable("schedule_id") long scheduleId,
                                              @PathVariable("month_id") long monthId) {
        return ResponseEntity.ok(monthService.delete(scheduleId, monthId));
    }

}
