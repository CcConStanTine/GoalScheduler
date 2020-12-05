package com.pk.ms.services.schedule;

import com.pk.ms.dao.schedule.ScheduleRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {

    @Mock
    ScheduleRepository scheduleRepo;

    MyScheduleUser user;
    Schedule schedule1;

    ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        user = new MyScheduleUser();
        schedule1 = new Schedule(user);
        schedule1.setScheduleId(1L);

        scheduleService = new ScheduleService(scheduleRepo);
    }

    @Test
    @DisplayName("Check if method getSchedule() throws ResourceNotAvailableException when Schedule with given id does not exist")
    void should_ThrowResourceNotAvailableException_When_ScheduleWithGivenIdDoNotExists() {
        //given
        given(scheduleRepo.findById(1L)).willReturn(null);
        //when + then
        assertThrows(ResourceNotAvailableException.class, () -> scheduleService.getScheduleById(1L));
        verify(scheduleRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getSchedule() return Schedule when Schedule with given id exists")
    void should_ReturnSchedule_When_ScheduleWithGivenIdExists() {
        //given
        given(scheduleRepo.findById(1L)).willReturn(java.util.Optional.ofNullable(schedule1));
        //when
        Schedule scheduleActual = scheduleService.getScheduleById(1L);
        //then
        verify(scheduleRepo, Mockito.times(1)).findById(1L);
        assertSame(schedule1, scheduleActual);
    }
}
