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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    @DisplayName("Check if method saveSchedule() call repository method save()")
    void should_MethodSaveScheduleCallRepositoryMethodSave() {
        //given
        given(scheduleRepo.findById(1L)).willReturn(Optional.of(schedule1));
        //when
        scheduleService.saveSchedule(schedule1);
        //
        verify(scheduleRepo, Mockito.times(1)).save(schedule1);
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
}
