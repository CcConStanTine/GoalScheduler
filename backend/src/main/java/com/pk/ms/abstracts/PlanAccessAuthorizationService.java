package com.pk.ms.abstracts;

public interface PlanAccessAuthorizationService extends AccessAuthorizationService {

    default <T extends PlanEntity> boolean hasAccess(long scheduleId, T t) {
        return t.getSchedule().getScheduleId() == scheduleId;
    }

}
