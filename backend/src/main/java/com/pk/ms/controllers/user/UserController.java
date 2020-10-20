package com.pk.ms.controllers.user;

import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.dto.user.UserInputDTO;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create a User and assign a new Schedule to him
    @PostMapping(consumes = "application/json")
    public ResponseEntity<MyScheduleUser> createUser(@Valid @RequestBody UserInputDTO userInputDTO) {
        return ResponseEntity.ok(userService.createUser(userInputDTO));
    }

    // get particular User info
    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("id") long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    // update a User
    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserInputDTO userInputDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userInputDTO));
    }

}
