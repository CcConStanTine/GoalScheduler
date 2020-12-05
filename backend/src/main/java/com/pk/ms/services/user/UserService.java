package com.pk.ms.services.user;

import com.pk.ms.dao.user.UserRepository;
import com.pk.ms.dto.user.UserEmailUpdateDTO;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserPasswordUpdateDTO;
import com.pk.ms.dto.user.UserBasicInfoUpdateDTO;
import com.pk.ms.entities.user.Image;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.exceptions.UniqueValuesAlreadyExistsException;
import com.pk.ms.mappers.user.UserInfoMapService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserInfoMapService userInfoMapService;

    private final PasswordEncoder encoder;

    private final ImageService imageService;

    public UserService(UserRepository repository, UserInfoMapService userInfoMapService, PasswordEncoder encoder, ImageService imageService) {
        this.repository = repository;
        this.userInfoMapService = userInfoMapService;
        this.encoder = encoder;
        this.imageService = imageService;
    }

    public MyScheduleUser saveUser(MyScheduleUser user) {
        return repository.save(user);
    }

    public MyScheduleUser getUserById(long id) {
        return getNotNullUserById(id);
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
        return repository.findById(id).orElseThrow(ResourceNotAvailableException::new);
    }

    private MyScheduleUser getByNick(String nick) {
        return repository.findByNick(nick);
    }

    private MyScheduleUser getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Image getUserImage(long userId) {
        Image image = getNotNullUserById(userId).getImage();
        if(image == null)
            return new Image("https://res.cloudinary.com/ccconstantine/image/upload/v1607097618/person_tgl8si.png");
        else
            return image;
    }

    public Image addUserImage(long userId, MultipartFile file) {
        MyScheduleUser user = getNotNullUserById(userId);
        if(user.getImage() == null) {
            Image image = imageService.saveImage(file);
            user.setImage(image);
            saveUser(user);
            return image;
        }
        else
            return updateUserImage(userId, file);
    }

    private Image updateUserImage(long userId, MultipartFile file) {
        return imageService.uploadImage(getNotNullUserById(userId).getImage(), file);
    }

    public String deleteUserImage(long userId) {
        MyScheduleUser user = getNotNullUserById(userId);
        Image image = user.getImage();
        user.setImage(null);
        saveUser(user);
        return imageService.deleteImage(image);
    }

}
