package com.pk.ms.services.day;

import com.pk.ms.dao.day.DailyRoutinePlanRepository;
import com.pk.ms.dto.day.DailyRoutinePlanInputDTO;
import com.pk.ms.entities.day.DailyRoutinePlan;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRoutinePlanService {

    private final DailyRoutinePlanRepository repository;

    private final UserService userService;

    public DailyRoutinePlanService(DailyRoutinePlanRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<DailyRoutinePlan> getDailyRoutinePlans(long userId) {
        return repository.findAllByMyScheduleUserId(userId);
    }

    public DailyRoutinePlan getDailyRoutinePlan(long userId, long dailyRoutinePlanId) {
        return getAuthorizedNotNullDailyRoutinePlanById(userId, dailyRoutinePlanId);
    }

    public DailyRoutinePlan createDailyRoutinePlan(long userId, DailyRoutinePlanInputDTO dto) {
        DailyRoutinePlan plan = new DailyRoutinePlan(dto.getContent(), dto.getStartDate(),
                dto.getEndDate(), userService.getUserById(userId));
        updateImportance(dto, plan);
        updateUrgency(dto, plan);
        return plan;
    }

    public DailyRoutinePlan updateDailyRoutinePlan(long userId, long dailyRoutinePlanId,
                                                   DailyRoutinePlanInputDTO dto) {
        DailyRoutinePlan plan = getAuthorizedNotNullDailyRoutinePlanById(userId, dailyRoutinePlanId);
        updateDailyRoutinePlanAttributes(dto, plan);
        return save(plan);
    }

    public String deleteDailyRoutinePlan(long userId, long dailyRoutinePlanId) {
        delete(getAuthorizedNotNullDailyRoutinePlanById(userId, dailyRoutinePlanId));
        return "Plan deleted successfully. ";
    }

    private DailyRoutinePlan getAuthorizedNotNullDailyRoutinePlanById(long userId, long dailyRoutinePlanId) {
        DailyRoutinePlan plan = repository.findById(dailyRoutinePlanId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(userId, plan));
        return plan;
    }

    private void authorize(boolean b) {
        if(!b)
            throw new AccessDeniedException("Access denied. ");
    }

    boolean hasAccess(long userId, DailyRoutinePlan plan) {
        return plan.getUser().getUserId() == userId;
    }

    private DailyRoutinePlan save(DailyRoutinePlan plan) {
        return repository.save(plan);
    }

    private void updateDailyRoutinePlanAttributes(DailyRoutinePlanInputDTO dto, DailyRoutinePlan plan) {
        plan.setContent(dto.getContent());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        updateImportance(dto, plan);
        updateUrgency(dto, plan);
    }

    private void updateImportance(DailyRoutinePlanInputDTO dto, DailyRoutinePlan plan) {
        if(dto.getImportance() != null)
            plan.setImportance(dto.getImportance());
    }

    private void updateUrgency(DailyRoutinePlanInputDTO dto, DailyRoutinePlan plan) {
        if(dto.getUrgency() != null)
            plan.setUrgency(dto.getUrgency());
    }

    private void delete(DailyRoutinePlan plan) {
        repository.delete(plan);
    }
}
