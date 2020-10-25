package com.pk.ms.services.user;

import com.pk.ms.dao.user.IUserRepository;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserInputDTO;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.UniqueValuesAlreadyExistsException;
import com.pk.ms.mappers.user.UserInfoMapService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepo;

    private final UserInfoMapService userInfoMapService;

    public UserService(IUserRepository userRepo, UserInfoMapService userInfoMapService) {
        this.userRepo = userRepo;
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

    public UserInfoDTO getUserInfo(long id) {
        return userInfoMapService.mapToDTO(getUserById(id));
    }

    public UserInfoDTO updateUser(long userId, UserInputDTO userInputDTO) {

        if(checkForUniqueNick(userInputDTO.getNick()))
                throw new UniqueValuesAlreadyExistsException(userInputDTO);

        if(checkForUniqueEmail(userInputDTO.getEmail()))
                throw new UniqueValuesAlreadyExistsException(userInputDTO.getEmail());

        MyScheduleUser user = getUserById(userId);

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
