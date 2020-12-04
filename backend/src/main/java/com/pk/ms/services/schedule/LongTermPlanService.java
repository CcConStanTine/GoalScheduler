package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.LongTermPlanRepository;
import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.abstracts.AccessAuthorizationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LongTermPlanService implements AccessAuthorizationService {

    private final LongTermPlanRepository repository;

    private final ScheduleService scheduleService;

    public LongTermPlanService(LongTermPlanRepository repository, ScheduleService scheduleService) {
        this.repository = repository;
        this.scheduleService = scheduleService;
    }

    public List<LongTermPlan> getLongTermPlansByScheduleId(long id) {
        return repository.findAllByScheduleId(id);
    }

    public LongTermPlan createLongTermPlan(long scheduleId, LongTermPlanInputDTO ltpInputDTO) {
        return save(new LongTermPlan(ltpInputDTO.getContent(),
                                                ltpInputDTO.getStartDate(),
                                                ltpInputDTO.getEndDate(),
                                                ltpInputDTO.getImportance(),
                                                ltpInputDTO.getUrgency(),
                                                scheduleService.getScheduleById(scheduleId)));
    }

    public LongTermPlan updateLongTermPlan(long scheduleId, long ltpId, LongTermPlanInputDTO ltpInputDTO) {
        LongTermPlan longTermPlan = getAuthorizedNotNullLongTermPlanByIdFromRepo(scheduleId, ltpId);
        updateLTPAttributes(ltpInputDTO, longTermPlan);
        return save(longTermPlan);
    }

    public String deleteLongTermPlan(long scheduleId, long ltpId) {
        LongTermPlan longTermPlan = getAuthorizedNotNullLongTermPlanByIdFromRepo(scheduleId, ltpId);
        delete(longTermPlan);
        return "Plan has been deleted successfully. ";
    }

    public LongTermPlan updateFulfilledStatus(long scheduleId, long ltpId) {
        LongTermPlan longTermPlan = getAuthorizedNotNullLongTermPlanByIdFromRepo(scheduleId, ltpId);
        longTermPlan.setFulfilled(!longTermPlan.isFulfilled());
        return save(longTermPlan);
    }


    private LongTermPlan getAuthorizedNotNullLongTermPlanByIdFromRepo(long scheduleId, long ltpId) {
        LongTermPlan longTermPlan = repository.findById(ltpId)
                .orElseThrow(ResourceNotAvailableException::new);
        authorize(hasAccess(scheduleId, longTermPlan));
        return longTermPlan;
    }

    private LongTermPlan save(LongTermPlan longTermPlan) {
        return repository.save(longTermPlan);
    }

    private void updateLTPAttributes(LongTermPlanInputDTO ltpInputDTO, LongTermPlan longTermPlan) {
        longTermPlan.setContent(ltpInputDTO.getContent());
        longTermPlan.setStartDate(ltpInputDTO.getStartDate());
        longTermPlan.setEndDate(ltpInputDTO.getEndDate());
        longTermPlan.setImportance(ltpInputDTO.getImportance());
        longTermPlan.setUrgency(ltpInputDTO.getUrgency());
    }

    private void delete(LongTermPlan longTermPlan) {
        repository.delete(longTermPlan);
    }
}
