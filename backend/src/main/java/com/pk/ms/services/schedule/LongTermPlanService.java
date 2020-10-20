package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.ILongTermPlanRepository;
import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
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

    public void deleteLongTermPlan(long id) {
        longTermPlanRepo.delete(longTermPlanRepo.findById(id));
    }

    public LongTermPlan createLongTermPlan(long scheduleId, LongTermPlanInputDTO ltpInputDTO) {
        return save(new LongTermPlan(ltpInputDTO.getContent(),
                ltpInputDTO.getStartDate(), ltpInputDTO.getEndDate(),
                scheduleService.getScheduleById(scheduleId)));
    }

    public LongTermPlan updateLongTermPlan(long ltpId, LongTermPlanInputDTO ltpInputDTO) {

        LongTermPlan longTermPlan = getLTPById(ltpId);

        if(ltpInputDTO.getContent() != null)
            longTermPlan.setContent(ltpInputDTO.getContent());
        if(ltpInputDTO.getStartDate() != null)
            longTermPlan.setStartDate(ltpInputDTO.getStartDate());
        if(ltpInputDTO.getEndDate() != null)
            longTermPlan.setEndDate(ltpInputDTO.getEndDate());

        return save(longTermPlan);
    }

}
