package com.pk.ms.controllers.user;

import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserInputDTO;
import com.pk.ms.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
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

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable("id") long id,
                                                  @Valid @RequestBody UserInputDTO userInputDTO) {

        return ResponseEntity.ok(userService.updateUser(id, userInputDTO));
    }

}
