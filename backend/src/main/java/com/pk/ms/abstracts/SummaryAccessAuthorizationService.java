package com.pk.ms.abstracts;

public interface SummaryAccessAuthorizationService extends AccessAuthorizationService {

    default <T extends Summary> boolean hasAccess(long scheduleId, T t) {
        return t.getSchedule().getScheduleId() == scheduleId;
    }

}
