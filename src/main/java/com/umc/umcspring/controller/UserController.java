package com.umc.umcspring.controller;

import com.umc.umcspring.handler.BaseException;
import com.umc.umcspring.handler.BaseResponse;
import com.umc.umcspring.model.User;
import com.umc.umcspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") int userId) throws BaseException {
        return userService.getUser(userId);
    }

    @PostMapping("/sign-up")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int userId,
                                             @RequestBody User userUpdate) throws BaseException {
        return userService.updateUser(userId, userUpdate);
    }

//    @PostMapping("/sign-up")
//    public User logIn(@RequestBody User userLogin){
//        try {
//            return userService.logIn;
//        } catch (BaseException exception) {
//            new BaseResponse<>(exception.getStatus());
//        }
//    }



}
