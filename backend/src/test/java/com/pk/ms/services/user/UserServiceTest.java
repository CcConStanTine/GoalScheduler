package com.pk.ms.services.user;

import com.pk.ms.dao.user.UserRepository;
import com.pk.ms.dto.user.UserBasicInfoUpdateDTO;
import com.pk.ms.dto.user.UserEmailUpdateDTO;
import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserPasswordUpdateDTO;
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

    UserInfoMapService userInfoMapService;

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
        userService = new UserService(userRepo, userInfoMapService, encoder);
    }

    @Test
    @DisplayName("Check if method saveUser() returns saved user")
    void should_ReturnSavedUser() {
        //given
        given(userRepo.save(user1)).willReturn(user1);
        //when
        MyScheduleUser user3 = userService.saveUser(user1);
        //then
        assertSame(user3, user1);
    }

    @Test
    @DisplayName("Check if method checkIfUniqueNick() returns true when nick is unique")
    void should_ReturnTrue_When_NickIsUnique() {
        //given
        given(userRepo.findByNick("uniqueNick")).willReturn(null);
        //when
        boolean result = userService.isNickUnique("uniqueNick");
        //then
        verify(userRepo, Mockito.times(1)).findByNick(anyString());
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if method checkIfUniqueNick() returns false when nick is not unique")
    void should_ReturnFalse_When_NickIsNotUnique() {
        //given
        given(userRepo.findByNick("CaptainJohn")).willReturn(user1);
        //when
        boolean result = userService.isNickUnique("CaptainJohn");
        //then
        verify(userRepo, Mockito.times(1)).findByNick(anyString());
        assertFalse(result);
    }

    @Test
    @DisplayName("Check if method checkIfUniqueEmail() returns true when email is unique")
    void should_ReturnTrue_When_EmailIsUnique() {
        //given
        given(userRepo.findByEmail("uniqueEmail@gmail.com")).willReturn(null);
        //when
        boolean result = userService.isEmailUnique("uniqueEmail@gmail.com");
        //then
        verify(userRepo, Mockito.times(1)).findByEmail(anyString());
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if method checkIfUniqueEmail() returns false when email is not unique")
    void should_ReturnFalse_When_EmailIsNotUnique() {
        //given
        given(userRepo.findByEmail("johnbravo@gmail.com")).willReturn(user1);
        //when
        boolean result = userService.isEmailUnique("johnbravo@gmail.com");
        //then
        verify(userRepo, Mockito.times(1)).findByEmail(anyString());
        assertFalse(result);
    }

    @Test
    @DisplayName("Check if method getUserInfo() throws ResourceNotAvailableException when User is null")
    void should_MethodGetUserInfoThrowResourceNotAvailableException_When_UserIsNull() {
        //given
        given(userRepo.findById(3L)).willReturn(null);
        //when + then
        assertThrows(ResourceNotAvailableException.class, () -> userService.getUserInfo(3L));
    }

    @Test
    @DisplayName("Check if method getUserInfo() returns mapped UserInfoDTO when User is not null")
    void should_ReturnMappedUserInfoDTO_When_UserIsNotNull() {
        //given
        UserInfoDTO userInfoDTOExpected = new UserInfoDTO(user1.getUserId(), user1.getFirstName(),
                user1.getLastName(), user1.getNick(), user1.getEmail());
        given(userRepo.findById(1L)).willReturn(user1);
        //when
        UserInfoDTO userInfoDTOActual = userService.getUserInfo(1L);
        //then
        assertAll(
                () -> assertEquals(userInfoDTOExpected.getUserId(),     userInfoDTOActual.getUserId()),
                () -> assertEquals(userInfoDTOExpected.getFirstName(),  userInfoDTOActual.getFirstName()),
                () -> assertEquals(userInfoDTOExpected.getLastName(),   userInfoDTOActual.getLastName()),
                () -> assertEquals(userInfoDTOExpected.getNick(),       userInfoDTOActual.getNick()),
                () -> assertEquals(userInfoDTOExpected.getEmail(),      userInfoDTOActual.getEmail())
        );
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() throws ResourceNotAvailableException when User is null")
    void should_MethodUpdateBasicUserInfoThrowResourceNotAvailableException_When_UserIsNull() {
        //given
        UserBasicInfoUpdateDTO userBasicInfoUpdateDTO = new UserBasicInfoUpdateDTO();
        given(userRepo.findById(3L)).willReturn(null);
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.updateBasicUserInfo(3L, userBasicInfoUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() throws UniqueValuesAlreadyExistsException when input nick is not unique")
    void should_ThrowUniqueValuesAlreadyExistsException_When_InputNickIsNotUnique() {
        //given
        UserBasicInfoUpdateDTO userBasicInfoUpdateDTO = new UserBasicInfoUpdateDTO(user1.getFirstName(),
                user1.getLastName(), user1.getNick());
        given(userRepo.findByNick("CaptainJohn")).willReturn(user1);
        //when + then
        assertThrows(UniqueValuesAlreadyExistsException.class,
                () -> userService.updateBasicUserInfo(1L, userBasicInfoUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateBasicUserInfo() update user attributes when input nick is unique")
    void should_UpdateUserAttributes_When_InputNickIsUnique() {
        //given
        UserBasicInfoUpdateDTO userBasicInfoUpdateDTO = new UserBasicInfoUpdateDTO("UniqueFirstName",
                "UniqueLastName", "UniqueNick");
        given(userRepo.findById(1L)).willReturn(user1);
        given(userRepo.save(user1)).willReturn(user1);
        given(userRepo.findByNick("UniqueNick")).willReturn(null);
        //when
        UserInfoDTO userInfoDTOActual = userService.updateBasicUserInfo(1L, userBasicInfoUpdateDTO);
        //then
        assertAll(
                () -> assertEquals(user1.getFirstName(),   userInfoDTOActual.getFirstName()),
                () -> assertEquals(user1.getLastName(),    userInfoDTOActual.getLastName()),
                () -> assertEquals(user1.getNick(),        userInfoDTOActual.getNick())
        );
    }

    @Test
    @DisplayName("Check if method updateUserEmail() throws ResourceNotAvailableException when User is null")
    void should_MethodUpdateUserEmailThrowResourceNotAvailableException_When_UserIsNull() {
        //given
        UserEmailUpdateDTO userEmailUpdateDTO = new UserEmailUpdateDTO();
        given(userRepo.findById(3L)).willReturn(null);
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.updateUserEmail(3L, userEmailUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateUserEmail() throws UniqueValuesAlreadyExistsException when input email is not unique")
    void should_ThrowUniqueValuesAlreadyExistsException_When_InputEmailIsNotUnique() {
        //given
        UserEmailUpdateDTO userEmailUpdateDTO = new UserEmailUpdateDTO("johnbravo@gmail.com");
        given(userRepo.findByEmail("johnbravo@gmail.com")).willReturn(user1);
        //when + then
        assertThrows(UniqueValuesAlreadyExistsException.class,
                () -> userService.updateUserEmail(1L, userEmailUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateUserEmail() update user email when input email is unique")
    void should_UpdateUserEmail_When_InputEmailIsUnique() {
        //given
        UserEmailUpdateDTO userEmailUpdateDTO = new UserEmailUpdateDTO("UniqueEmail@gmail.com");
        given(userRepo.findById(1L)).willReturn(user1);
        given(userRepo.save(user1)).willReturn(user1);
        given(userRepo.findByEmail("UniqueEmail@gmail.com")).willReturn(null);
        //when
        UserInfoDTO userInfoDTOActual = userService.updateUserEmail(1L, userEmailUpdateDTO);
        //then
        assertEquals(user1.getEmail(), userEmailUpdateDTO.getEmail());
    }

    @Test
    @DisplayName("Check if method updateUserPassword() throws ResourceNotAvailableException when User is null")
    void should_MethodUpdateUserPasswordThrowResourceNotAvailableException_When_UserIsNull() {
        //given
        UserPasswordUpdateDTO userPasswordUpdateDTO = new UserPasswordUpdateDTO();
        given(userRepo.findById(3L)).willReturn(null);
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> userService.updateUserPassword(3L, userPasswordUpdateDTO));
    }

    @Test
    @DisplayName("Check if method updateUserPassword() update and encode user password")
    void should_UpdateAndEncodeUserPassword() {
        //given
        UserPasswordUpdateDTO userPasswordUpdateDTO = new UserPasswordUpdateDTO("newPassword");
        given(userRepo.findById(1L)).willReturn(user1);
        given(userRepo.save(user1)).willReturn(user1);
        //when
        userService.updateUserPassword(1L, userPasswordUpdateDTO);
        //then
        assertNotEquals(userPasswordUpdateDTO.getPassword(), user1.getPassword());
    }

    @Test
    @DisplayName("Check if method updateUserPassword() returns proper message")
    void should_ReturnProperMessage() {
        //given
        UserPasswordUpdateDTO userPasswordUpdateDTO = new UserPasswordUpdateDTO("newPassword");
        given(userRepo.findById(1L)).willReturn(user1);
        given(userRepo.save(user1)).willReturn(user1);
        //when
        String message = userService.updateUserPassword(1L, userPasswordUpdateDTO);
        //then
        assertEquals("Password changed successfully. ", message);
    }
}