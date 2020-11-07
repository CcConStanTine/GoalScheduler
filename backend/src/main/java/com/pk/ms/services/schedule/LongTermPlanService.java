package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.LongTermPlanRepository;
import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LongTermPlanService {

    private final LongTermPlanRepository longTermPlanRepo;

    private final ScheduleService scheduleService;

    public LongTermPlanService(LongTermPlanRepository longTermPlanRepo, ScheduleService scheduleService) {
        this.longTermPlanRepo = longTermPlanRepo;
        this.scheduleService = scheduleService;
    }

    public List<LongTermPlan> getLongTermPlansByScheduleId(long id) {
        return getLongTermPlansByScheduleIdFromRepo(id);
    }

    public LongTermPlan createLongTermPlan(long scheduleId, LongTermPlanInputDTO ltpInputDTO) {
        return saveLongTermPLan(new LongTermPlan(ltpInputDTO.getContent(),
                ltpInputDTO.getStartDate(), ltpInputDTO.getEndDate(),
                scheduleService.getScheduleById(scheduleId)));
    }

    public LongTermPlan updateLongTermPlan(long scheduleId, long ltpId, LongTermPlanInputDTO ltpInputDTO) {

        LongTermPlan longTermPlan = getNotNullLTPById(ltpId);

        if(hasAccess(scheduleId, longTermPlan)) {

            String content = ltpInputDTO.getContent();
            if (!isObjectNull(content))
                longTermPlan.setContent(content);

            LocalDate startDate = ltpInputDTO.getStartDate();
            if (!isObjectNull(startDate))
                longTermPlan.setStartDate(startDate);

            LocalDate endDate = ltpInputDTO.getEndDate();
            if (!isObjectNull(endDate))
                longTermPlan.setEndDate(endDate);

            return saveLongTermPLan(longTermPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }

    public String deleteLongTermPlan(long scheduleId, long ltpId) {
        LongTermPlan longTermPlan = getNotNullLTPById(ltpId);
        if(hasAccess(scheduleId, longTermPlan)) {
            deleteLongTermPlan(longTermPlan);
            return "Plan has been deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }

    public LongTermPlan updateFulfilledStatus(long scheduleId, long ltpId) {
        LongTermPlan longTermPlan = getNotNullLTPById(ltpId);
        if(hasAccess(scheduleId, longTermPlan)) {
            boolean fulfilled = longTermPlan.isFulfilled();
            fulfilled = !fulfilled;
            longTermPlan.setFulfilled(fulfilled);
            return saveLongTermPLan(longTermPlan);
        }
        else
            throw new AccessDeniedException("This user cannot update this resource. ");
    }


    private List<LongTermPlan> getLongTermPlansByScheduleIdFromRepo(long id) {
        return longTermPlanRepo.findAllByScheduleId(id);
    }

    private LongTermPlan getNotNullLTPById(long id) {
        LongTermPlan longTermPlan = getLongTermPlanByIdFromRepo(id);
        if(isObjectNull(longTermPlan))
            throw new ResourceNotAvailableException();
        return longTermPlan;
    }

    private LongTermPlan getLongTermPlanByIdFromRepo(long id) {
        return longTermPlanRepo.findById(id);
    }

    private LongTermPlan saveLongTermPLan(LongTermPlan longTermPlan) {
        return longTermPlanRepo.save(longTermPlan);
    }

    private void deleteLongTermPlan(LongTermPlan longTermPlan) {
        longTermPlanRepo.delete(longTermPlan);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private boolean hasAccess(long scheduleId, LongTermPlan longTermPlan) {
        return longTermPlan.getSchedule().getScheduleId() == scheduleId;
    }
}
