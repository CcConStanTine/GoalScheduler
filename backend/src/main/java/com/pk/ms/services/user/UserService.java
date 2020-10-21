package com.pk.ms.services.user;

import com.pk.ms.dao.user.IUserRepository;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.UniqueValuesAlreadyExistsException;
import com.pk.ms.mappers.user.UserInfoMapService;
import com.pk.ms.services.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final IUserRepository userRepo;

    private final ScheduleService scheduleService;

    private final UserInfoMapService userInfoMapService;

    public UserService(IUserRepository userRepo, ScheduleService scheduleService, UserInfoMapService userInfoMapService) {
        this.userRepo = userRepo;
        this.scheduleService = scheduleService;
        this.userInfoMapService = userInfoMapService;
    }

    public MyScheduleUser save(MyScheduleUser user) {
        return userRepo.save(user);
    }

    public MyScheduleUser getUserById(long id) {return userRepo.findById(id);}

    public boolean checkForUniqueNick(String nick) {
        if (userRepo.findByNick(nick) == null)
            return false;
        return true;
    }

    public boolean checkForUniqueEmail(String email) {
        if (userRepo.findByEmail(email) == null)
            return false;
        return true;
    }

    public MyScheduleUser createUser(UserInputDTO userInputDTO) {
        if(checkForUniqueNick(userInputDTO.getNick()))
            throw new UniqueValuesAlreadyExistsException(userInputDTO);
        if(checkForUniqueEmail(userInputDTO.getEmail()))
            throw new UniqueValuesAlreadyExistsException(userInputDTO.getEmail());

        MyScheduleUser user = new MyScheduleUser(userInputDTO.getFirstName(), userInputDTO.getLastName(),
                userInputDTO.getNick(), userInputDTO.getEmail());
        Schedule schedule = new Schedule(user, new ArrayList<Year>(), new ArrayList<LongTermPlan>());
        user = save(user);
        scheduleService.save(schedule);
        user.setSchedule(schedule);
        return save(user);
    }

    public UserInfoDTO getUserInfo(long id) {
        return userInfoMapService.mapToDTO(getUserById(id));
    }

    public UserInfoDTO updateUser(long id, UserInputDTO userInputDTO) {

        MyScheduleUser user = getUserById(id);

        if(userInputDTO.getFirstName() != null)
            user.setFirstName(userInputDTO.getFirstName());
        if(userInputDTO.getLastName() != null)
            user.setLastName(userInputDTO.getLastName());
        if(userInputDTO.getEmail() != null)
            user.setEmail(userInputDTO.getEmail());
        if(userInputDTO.getNick() != null)
            user.setNick(userInputDTO.getNick());

        save(user);
        return userInfoMapService.mapToDTO(user);
    }

}
