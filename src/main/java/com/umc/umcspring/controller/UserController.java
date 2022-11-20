package com.umc.umcspring.controller;

import com.umc.umcspring.dto.PostUserResDto;
import com.umc.umcspring.dto.UserReqDto;
import com.umc.umcspring.dto.UserResDto;
import com.umc.umcspring.handler.BaseException;
import com.umc.umcspring.handler.BaseResponse;
import com.umc.umcspring.model.User;
import com.umc.umcspring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.umcspring.handler.BaseResponseStatus.*;
import static com.umc.umcspring.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public BaseResponse<UserResDto> getUser(@PathVariable("userId") int userId) throws BaseException {
        try {
            UserResDto userResDto = userService.getUser(userId);
            return new BaseResponse<>(userResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @PostMapping("/sign-up")
    public BaseResponse<PostUserResDto> createUser(@RequestBody UserReqDto userReqDto) throws BaseException {
        if (userReqDto.getNickname() == null) {
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }
        if (userReqDto.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if (!isRegexEmail(userReqDto.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        if (userService.checkEmailDuplicate(userReqDto)) {
            return new BaseResponse<>(POST_USERS_EXISTS_EMAIL);
        }

        try {
            PostUserResDto postUserResDto = userService.createUser(userReqDto);
            return new BaseResponse<>(postUserResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @PostMapping("/log-in")
    public BaseResponse<PostUserResDto> logIn(@RequestBody UserReqDto userReqDto) {
        if (userReqDto.getEmail() == null) {
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

        try {
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostUserResDto postUserResDto = userService.logIn(userReqDto);
            return new BaseResponse<>(postUserResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @ResponseBody
    @PatchMapping("/user/{userId}")
    public BaseResponse<String> updateUser(@PathVariable("userId") int userId,
                                           @RequestBody UserReqDto userReqDto) throws BaseException {
        if (userService.checkNicknameDuplicate(userReqDto)) {
            return new BaseResponse<>(MODIFY_FAIL_USERNAME);
        }

        try{
            userService.updateUser(userId, userReqDto);
            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        }   catch(BaseException exception){
        return new BaseResponse<>((exception.getStatus()));
        }
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
