package com.pk.ms.services.user;

import com.pk.ms.dao.user.IUserRepository;
import com.pk.ms.dto.user.UserEmailUpdateDTO;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserPasswordUpdateDTO;
import com.pk.ms.dto.user.UserUpdateDTO;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.UniqueValuesAlreadyExistsException;
import com.pk.ms.mappers.user.UserInfoMapService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepo;

    private final UserInfoMapService userInfoMapService;

    private final PasswordEncoder encoder;

    public UserService(IUserRepository userRepo, UserInfoMapService userInfoMapService, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.userInfoMapService = userInfoMapService;
        this.encoder = encoder;
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

    public UserInfoDTO updateUser(long userId, UserUpdateDTO userUpdateDTO) {

        String nick = userUpdateDTO.getNick();

        if(checkForUniqueNick(userUpdateDTO.getNick()))
                throw new UniqueValuesAlreadyExistsException(userUpdateDTO);

        MyScheduleUser user = getUserById(userId);

        String firstName = userUpdateDTO.getFirstName();
        if(isInputNotNull(firstName));
            user.setFirstName(firstName);

        String lastName = userUpdateDTO.getLastName();
        if(isInputNotNull(lastName))
            user.setLastName(lastName);

        if(isInputNotNull(nick))
            user.setNick(nick);

        save(user);
        return userInfoMapService.mapToDTO(user);
    }

    private boolean isInputNotNull(String inputString) {
        if(inputString != null)
            return true;
        else
            return false;
    }

    public UserInfoDTO updateUserEmail(long userId, UserEmailUpdateDTO userEmailUpdateDTO) {

        String updateEmail = userEmailUpdateDTO.getEmail();

        if(checkForUniqueEmail(updateEmail))
            throw new UniqueValuesAlreadyExistsException(updateEmail);

        MyScheduleUser user = getUserById(userId);
        if(isInputNotNull(updateEmail))
            user.setEmail(updateEmail);

        save(user);
        return userInfoMapService.mapToDTO(user);
    }

    public String updateUserPassword(long userId, UserPasswordUpdateDTO userPasswordUpdateDTO) {
        MyScheduleUser user = getUserById(userId);
        String password = userPasswordUpdateDTO.getPassword();
        user.setPassword(encoder.encode(password));
        save(user);
        return "Password changed successfully. ";
    }
}
