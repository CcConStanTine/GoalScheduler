package com.pk.ms.services.user;

import com.pk.ms.constants.URL;
import com.pk.ms.dao.user.UserRepository;
import com.pk.ms.dto.user.UserBasicInfoUpdateDTO;
import com.pk.ms.dto.user.UserEmailUpdateDTO;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserPasswordUpdateDTO;
import com.pk.ms.entities.user.Image;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.exceptions.UniqueValuesAlreadyExistsException;
import com.pk.ms.mappers.user.UserInfoMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepo;

    @Mock
    UserInfoMapService userInfoMapService;

    @Mock
    ImageService imageService;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    MyScheduleUser user1;
    MyScheduleUser user2;

    UserService userService;

    @BeforeEach
    void setUp() {
        user1 = new MyScheduleUser("John", "Bravo",
                "CaptainJohn", "johnbravo@gmail.com", "password123");
        user2 = new MyScheduleUser("Pablo", "Picasso",
                "Picasso", "pablo@gmail.com", "password123");
        user1.setUserId(1L);
        user2.setUserId(2L);
        userInfoMapService = new UserInfoMapService();
        userService = new UserService(userRepo, userInfoMapService, encoder, imageService);
    }

    @Test
    @DisplayName("Check if method saveUser() calls repository method save()")
    void should_MethodSaveUserCallRepositoryMethodSave() {
        //given
        given(userRepo.save(user1)).willReturn(user1);
        //when
        userService.saveUser(user1);
        //then
        verify(userRepo, Mockito.times(1)).save(user1);
    }

    @Test
    @DisplayName("Check if method saveUser() returns MyScheduleUser class type")
    void should_MethodSaveReturnsMyScheduleUserClassType() {
        //given
        given(userRepo.save(user1)).willReturn(user1);
        //when + when
        assertEquals(MyScheduleUser.class, userService.saveUser(user1).getClass());
    }

    @Test
    @DisplayName("Check if method getUserById() calls repository method findById()")
    void should_MethodGetUserByIdCallsRepositoryMethodFindById() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.getUserById(1L);
        //then
        verify(userRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getUserById() throws ResourceNotAvailableException when user with given id does not exist")
    void should_MethodGetUserByIdThrowsResourceNotAvailableException_When_UserWithGivenIdDoesNotExist() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.empty());
        //when+then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.getUserById(1L));
    }

    @Test
    @DisplayName("Check if method getUserById() returns MyScheduleUser class type")
    void should_MethodGetUserByIdReturnsMyScheduleUserClassType() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when + when
        assertEquals(MyScheduleUser.class, userService.getUserById(1L).getClass());
    }

    @Test
    @DisplayName("Check if method isNickUnique() calls repository method existsByNick()")
    void should_MethodIsNickUniqueCallsRepositoryMethodFindByNick() {
        //given
        given(userRepo.existsByNick("CaptainJohn")).willReturn(true);
        //when
        userService.isNickUnique("CaptainJohn");
        //then
        verify(userRepo, Mockito.times(1)).existsByNick("CaptainJohn");
    }

    @Test
    @DisplayName("Check if method isUniqueNick() returns true when nick is unique")
    void should_MethodIsNickUniqueReturnsTrue_When_NickIsUnique() {
        //given
        given(userRepo.existsByNick("uniqueNick")).willReturn(true);
        //when
        boolean result = userService.isNickUnique("uniqueNick");
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if method isUniqueNick() returns false when nick is not unique")
    void should_MethodIsNickUniqueReturnsFalse_When_NickIsNotUnique() {
        //given
        given(userRepo.existsByNick("CaptainJohn")).willReturn(false);
        //when
        boolean result = userService.isNickUnique("CaptainJohn");
        //then
        verify(userRepo, Mockito.times(1)).existsByNick(anyString());
        assertFalse(result);
    }

    @Test
    @DisplayName("Check if method isUniqueEmail() calls repository method existsByEmail()")
    void should_MethodIsUniqueEmailCallsRepositoryMethodExistsMyEmail() {
        //given
        given(userRepo.existsByEmail("johnbravo@gmail.com")).willReturn(true);
        //when
        userService.isEmailUnique("johnbravo@gmail.com");
        //then
        verify(userRepo, Mockito.times(1)).existsByEmail("johnbravo@gmail.com");
    }

    @Test
    @DisplayName("Check if method isUniqueEmail() returns true when email is unique")
    void should_MethodIsEmailUniqueReturnsTrue_When_EmailIsUnique() {
        //given
        given(userRepo.existsByEmail("johnbravo@gmail.com")).willReturn(true);
        //when
        boolean result = userService.isEmailUnique("johnbravo@gmail.com");
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if method isUniqueEmail() returns false when email is not unique")
    void should_MethodIsEmailUniqueReturnsFalse_When_EmailIsNotUnique() {
        //given
        given(userRepo.existsByEmail("johnbravo@gmail.com")).willReturn(false);
        //when
        boolean result = userService.isEmailUnique("johnbravo@gmail.com");
        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("Check if method getUserInfo() calls repository method findById()")
    void should_MethodGetUserInfoCallsRepositoryMethodFindById() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.getUserInfo(1L);
        //then
        verify(userRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getUserInfo() throws ResourceNotAvailableException when user with given id does not exist")
    void should_MethodGetUserInfoThrowsResourceNotAvailableException_When_UserWithGivenIdDoesNotExist() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.empty());
        //when+then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.getUserInfo(1L));
    }

    @Test
    @DisplayName("Check if method getUserInfo() returns UserInfoDTO class type")
    void should_MethodGetUserInfoReturnsMyScheduleUserClassType() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when + when
        assertEquals(UserInfoDTO.class, userService.getUserInfo(1L).getClass());
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() throws UniqueValuesAlreadyExistsException when nick is not unique")
    void should_MethodUpdateBasicUserInfoThrowsUniqueValuesAlreadyExistsException_When_NickIsNotUnique() {
        //given
        UserBasicInfoUpdateDTO userBasicInfoUpdateDTO = new UserBasicInfoUpdateDTO(user1.getFirstName(),
                user1.getLastName(), user1.getNick());
        given(userRepo.existsByNick("CaptainJohn")).willReturn(true);
        //when + then
        assertThrows(UniqueValuesAlreadyExistsException.class,
                () -> userService.updateBasicUserInfo(1L, userBasicInfoUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() calls repository method findById()")
    void should_MethodUpdateBasicUserInfoCallsRepositoryMethodFindById() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.updateBasicUserInfo(1L, new UserBasicInfoUpdateDTO());
        //then
        verify(userRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() throws ResourceNotAvailableException when user with given id does not exist")
    void should_MethodUpdateBasicUserInfoThrowsResourceNotAvailableException_When_UserWithGivenIdDoesNotExist() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.empty());
        //when+then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.updateBasicUserInfo(1L, new UserBasicInfoUpdateDTO()));
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() calls repository method save()")
    void should_MethodUpdateBasicUserInfoCallsRepositoryMethodSave() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        given(userRepo.save(user1)).willReturn(user1);
        //when
        userService.updateBasicUserInfo(1L, new UserBasicInfoUpdateDTO());
        //then
        verify(userRepo, Mockito.times(1)).save(user1);
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() updates MyScheduleUser's basic fields when they are not nulls")
    void should_MethodUpdateBasicUserInfoUpdatesMyScheduleUserBasicFieldsWhenTheyAreNotNulls() {
        //given
        UserBasicInfoUpdateDTO userBasicInfoUpdateDTO = new UserBasicInfoUpdateDTO(
                "NewFirstName", "NewLastName", "NewNick");
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.updateBasicUserInfo(1L, userBasicInfoUpdateDTO);
        //then
        assertAll(
                () -> assertEquals(userBasicInfoUpdateDTO.getFirstName(), user1.getFirstName()),
                () -> assertEquals(userBasicInfoUpdateDTO.getLastName(), user1.getLastName()),
                () -> assertEquals(userBasicInfoUpdateDTO.getNick(), user1.getNick())
        );
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() do not update MyScheduleUser's basic fields when they are nulls")
    void should_MethodUpdateBasicUserInfoDoNotUpdateMyScheduleUserBasicFieldsWhenTheyAreNulls() {
        //given
        UserBasicInfoUpdateDTO updateUserBasicInfoUpdateDTO = new UserBasicInfoUpdateDTO();
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        UserBasicInfoUpdateDTO expectedValues = new UserBasicInfoUpdateDTO(
                user1.getFirstName(), user1.getLastName(), user1.getNick());
        //when
        userService.updateBasicUserInfo(1L, updateUserBasicInfoUpdateDTO);
        //then
        assertAll(
                () -> assertEquals(expectedValues.getFirstName(), user1.getFirstName()),
                () -> assertEquals(expectedValues.getLastName(), user1.getLastName()),
                () -> assertEquals(expectedValues.getNick(), user1.getNick())
        );
    }

    @Test
    @DisplayName("Check if method updateUserEmail() throws UniqueValuesAlreadyExistsException when email is not unique")
    void should_MethodUpdateUserEmailThrowsUniqueValuesAlreadyExistsException_When_NickIsNotUnique() {
        //given
        UserEmailUpdateDTO userEmailUpdateDTO = new UserEmailUpdateDTO("johnbravo@gmail.com");
        given(userRepo.existsByEmail("johnbravo@gmail.com")).willReturn(true);
        //when + then
        assertThrows(UniqueValuesAlreadyExistsException.class,
                () -> userService.updateUserEmail(1L, userEmailUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateUserEmail() calls repository method findById()")
    void should_MethodUpdateUserEmailCallsRepositoryMethodFindById() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.updateUserEmail(1L, new UserEmailUpdateDTO());
        //then
        verify(userRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateUserEmail() throws ResourceNotAvailableException when user with given id does not exist")
    void should_MethodUpdateUserEmailThrowsResourceNotAvailableException_When_UserWithGivenIdDoesNotExist() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.empty());
        //when+then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.updateUserEmail(1L, new UserEmailUpdateDTO()));
    }

    @Test
    @DisplayName("Check if method updateUserEmail() calls repository method save()")
    void should_MethodUpdateUserEmailCallsRepositoryMethodSave() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        given(userRepo.save(user1)).willReturn(user1);
        //when
        userService.updateUserEmail(1L, new UserEmailUpdateDTO());
        //then
        verify(userRepo, Mockito.times(1)).save(user1);
    }

    @Test
    @DisplayName("Check if method updateUserEmail() updates MyScheduleUser's email")
    void should_MethodUpdateUserEmailUpdatesMyScheduleUserEmail() {
        //given
        UserEmailUpdateDTO userEmailUpdateDTO = new UserEmailUpdateDTO("somenewemaail@gmail.com");
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.updateUserEmail(1L, userEmailUpdateDTO);
        //then
        assertEquals(userEmailUpdateDTO.getEmail(), user1.getEmail());
    }

    @Test
    @DisplayName("Check if method updateUserPassword() calls repository method findById()")
    void should_MethodUpdateUserPasswordCallsRepositoryMethodFindById() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.updateUserPassword(1L, new UserPasswordUpdateDTO("pass1234"));
        //then
        verify(userRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateUserPassword() throws ResourceNotAvailableException when user with given id does not exist")
    void should_MethodUpdateUserPasswordThrowsResourceNotAvailableException_When_UserWithGivenIdDoesNotExist() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.empty());
        //when+then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.updateUserPassword(1L, new UserPasswordUpdateDTO()));
    }

    @Test
    @DisplayName("Check if method updateUserPassword() calls repository method save()")
    void should_MethodUpdateUserPasswordCallsRepositoryMethodSave() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        given(userRepo.save(user1)).willReturn(user1);
        //when
        userService.updateUserPassword(1L, new UserPasswordUpdateDTO("pass1234"));
        //then
        verify(userRepo, Mockito.times(1)).save(user1);
    }

    @Test
    @DisplayName("Check if method updateUserPassword() returns proper message")
    void should_ReturnProperMessage() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        given(userRepo.save(user1)).willReturn(user1);
        //when
        String message = userService.updateUserPassword(1L, new UserPasswordUpdateDTO("pass1234"));
        //then
        assertEquals("Password changed successfully. ", message);
    }

    @Test
    @DisplayName("Check if method getUserImage() calls repository method findById()")
    void should_MethodGetUserImageCallsRepositoryMethodFindById() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        userService.getUserImage(1L);
        //then
        verify(userRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getUserImage() throws ResourceNotAvailableException when user with given id does not exist")
    void should_MethodGetUserImageThrowsResourceNotAvailableException_When_UserWithGivenIdDoesNotExist() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.empty());
        //when+then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.getUserImage(1L));
    }

    @Test
    @DisplayName("Check if method getUserImage() returns Image with default URL1 when user has no image")
    void should_MethodGetUserImageReturnsImageWithDefaultURL1_When_UserHasNoImage() {
        //given
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        Image image = userService.getUserImage(1L);
        //then
        assertEquals(URL.URL1.getURL(), image.getFileUrl());
    }

    @Test
    @DisplayName("Check if method getUserImage() returns user's image when user has image assigned")
    void should_MethodGetUserImageReturnsUserImage_When_UserHasImageAssigned() {
        //given
        Image expectedImage = new Image("someURL");
        user1.setImage(expectedImage);
        given(userRepo.findById(1L)).willReturn(Optional.of(user1));
        //when
        Image actualImage = userService.getUserImage(1L);
        //then
        assertAll(
                () -> assertSame(expectedImage, actualImage),
                () -> assertEquals(expectedImage.getFileUrl(), actualImage.getFileUrl())
        );
    }
}