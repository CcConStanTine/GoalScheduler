package com.pk.ms.controllers.user;

import com.pk.ms.dto.user.*;
import com.pk.ms.entities.user.Image;
import com.pk.ms.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("id") long id) {

        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PatchMapping(path = "/{id}/basic", consumes = "application/json")
    public ResponseEntity<UserInfoDTO> updateBasicUserInfo(@PathVariable("id") long id,
                                                           @Valid @RequestBody UserBasicInfoUpdateDTO userBasicInfoUpdateDTO) {

        return ResponseEntity.ok(userService.updateBasicUserInfo(id, userBasicInfoUpdateDTO));
    }

    @PatchMapping(path = "/{id}/email", consumes = "application/json")
    public ResponseEntity<UserInfoDTO> updateUserEmail(@PathVariable("id") long id,
                                                       @Valid @RequestBody UserEmailUpdateDTO userEmailUpdateDTO) {

        return ResponseEntity.ok(userService.updateUserEmail(id ,userEmailUpdateDTO));
    }
    @PatchMapping(path = "/{id}/password", consumes = "application/json")
    public ResponseEntity<String> updateUserPassword(@PathVariable("id") long id,
                                                     @Valid @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {

        return ResponseEntity.ok(userService.updateUserPassword(id ,userPasswordUpdateDTO));
    }


    @GetMapping("/{id}/image")
    public ResponseEntity<Image> getUserImage(@PathVariable("id") long id) {

        return ResponseEntity.ok(userService.getUserImage(id));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Image> addUserImage(@PathVariable("id") long id,
                                              @RequestParam ("file") MultipartFile file) {

        return ResponseEntity.ok(userService.addUserImage(id, file));
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<String> deleteUserImage(@PathVariable("id") long id) {

        return ResponseEntity.ok(userService.deleteUserImage(id));
    }


}
