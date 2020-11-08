package com.pk.ms.services.user;

import com.pk.ms.dao.user.UserRepository;
import com.pk.ms.dto.user.UserEmailUpdateDTO;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserPasswordUpdateDTO;
import com.pk.ms.dto.user.UserBasicInfoUpdateDTO;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.exceptions.UniqueValuesAlreadyExistsException;
import com.pk.ms.mappers.user.UserInfoMapService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    private final UserInfoMapService userInfoMapService;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, UserInfoMapService userInfoMapService, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.userInfoMapService = userInfoMapService;
        this.encoder = encoder;
    }

    public MyScheduleUser saveUser(MyScheduleUser user) {
        return userRepo.save(user);
    }

    public boolean isNickUnique(String nick) {
        return isObjectNull(getByNick(nick));
    }

    public boolean isEmailUnique(String email) {
        return isObjectNull(getByEmail(email));
    }

    public UserInfoDTO getUserInfo(long id) {
        return userInfoMapService.mapToDTO(getNotNullUserById(id));
    }

    public UserInfoDTO updateBasicUserInfo(long userId, UserBasicInfoUpdateDTO userBasicInfoUpdateDTO) {

        String nick = userBasicInfoUpdateDTO.getNick();

        if(!isNickUnique(userBasicInfoUpdateDTO.getNick()))
            throw new UniqueValuesAlreadyExistsException(userBasicInfoUpdateDTO);

        MyScheduleUser user = getNotNullUserById(userId);

        String firstName = userBasicInfoUpdateDTO.getFirstName();
        if(!isObjectNull(firstName))
            user.setFirstName(firstName);

        String lastName = userBasicInfoUpdateDTO.getLastName();
        if(!isObjectNull(lastName))
            user.setLastName(lastName);

        if(!isObjectNull(nick))
            user.setNick(nick);

        saveUser(user);
        return userInfoMapService.mapToDTO(user);
    }

    public UserInfoDTO updateUserEmail(long userId, UserEmailUpdateDTO userEmailUpdateDTO) {

        String updateEmail = userEmailUpdateDTO.getEmail();

        if(!isEmailUnique(updateEmail))
            throw new UniqueValuesAlreadyExistsException(updateEmail);

        MyScheduleUser user = getNotNullUserById(userId);

        if(!isObjectNull(updateEmail))
            user.setEmail(updateEmail);

        saveUser(user);
        return userInfoMapService.mapToDTO(user);
    }

    public String updateUserPassword(long userId, UserPasswordUpdateDTO userPasswordUpdateDTO) {

        MyScheduleUser user = getNotNullUserById(userId);

        String password = userPasswordUpdateDTO.getPassword();
        user.setPassword(encoder.encode(password));
        saveUser(user);
        return "Password changed successfully. ";
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }

    private MyScheduleUser getNotNullUserById(long id) {
        MyScheduleUser user = userRepo.findById(id);
        if(isObjectNull(user))
            throw new ResourceNotAvailableException();
        return user;
    }

    private MyScheduleUser getByNick(String nick) {
        return userRepo.findByNick(nick);
    }

    private MyScheduleUser getByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
