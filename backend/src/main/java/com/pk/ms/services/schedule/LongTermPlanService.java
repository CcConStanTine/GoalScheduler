package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.ILongTermPlanRepository;
import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LongTermPlanService {

    private final ILongTermPlanRepository longTermPlanRepo;

    private final ScheduleService scheduleService;

    public LongTermPlanService(ILongTermPlanRepository longTermPlanRepo, ScheduleService scheduleService) {
        this.longTermPlanRepo = longTermPlanRepo;
        this.scheduleService = scheduleService;
    }

    public LongTermPlan getLTPById(long id) {
        return longTermPlanRepo.findById(id);
    }

    public List<LongTermPlan> getLongTermPlans(long id) { return longTermPlanRepo.findAllByScheduleId(id); }

    public LongTermPlan save(LongTermPlan longTermPlan) {
        return longTermPlanRepo.save(longTermPlan);
    }

    public String deleteLongTermPlan(long scheduleId, long ltpId) {
        LongTermPlan longTermPlan = getLTPById(ltpId);
        if(longTermPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, longTermPlan)) {
            longTermPlanRepo.deleteById(ltpId);
            return "Plan deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public LongTermPlan createLongTermPlan(long scheduleId, LongTermPlanInputDTO ltpInputDTO) {
        return save(new LongTermPlan(ltpInputDTO.getContent(),
                ltpInputDTO.getStartDate(), ltpInputDTO.getEndDate(),
                scheduleService.getScheduleById(scheduleId)));
    }

    public LongTermPlan updateLongTermPlan(long scheduleId, long ltpId, LongTermPlanInputDTO ltpInputDTO) {

        LongTermPlan longTermPlan = getLTPById(ltpId);
        if(longTermPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, longTermPlan)) {
            if (ltpInputDTO.getContent() != null)
                longTermPlan.setContent(ltpInputDTO.getContent());
            if (ltpInputDTO.getStartDate() != null)
                longTermPlan.setStartDate(ltpInputDTO.getStartDate());
            if (ltpInputDTO.getEndDate() != null)
                longTermPlan.setEndDate(ltpInputDTO.getEndDate());
            return save(longTermPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public LongTermPlan updateFulfilledStatus(long scheduleId, long longTermPlanId) {
        LongTermPlan longTermPlan = getLTPById(longTermPlanId);
        if(longTermPlan == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, longTermPlan)) {
            boolean fullfilled = longTermPlan.isFulfilled();
            if (!fullfilled)
                fullfilled = true;
            else
                fullfilled = false;
            longTermPlan.setFulfilled(fullfilled);
            return save(longTermPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    private boolean hasAccess(long scheduleId, LongTermPlan longTermPlan) {
        return longTermPlan.getSchedule().getScheduleId() == scheduleId;
    }

}
