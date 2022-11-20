package com.umc.umcspring.service;

import com.umc.umcspring.dto.PostUserResDto;
import com.umc.umcspring.dto.UserReqDto;
import com.umc.umcspring.dto.UserResDto;
import com.umc.umcspring.handler.BaseException;
import com.umc.umcspring.model.User;
import com.umc.umcspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.umc.umcspring.handler.BaseResponseStatus.*;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean checkEmailDuplicate(UserReqDto userReqDto) {
        return userRepository.existsByEmail(userReqDto.getEmail());
    }

    public boolean checkNicknameDuplicate(UserReqDto userReqDto) {
        return userRepository.existsByNickname(userReqDto.getNickname());
    }

    public PostUserResDto createUser(UserReqDto userReqDto) throws BaseException {
        String rawPassword = userReqDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userReqDto.setPassword(encPassword);

        userRepository.save(userReqDto.toEntity());

        try {
            int userIdx = userReqDto.getUserIdx();
            return new PostUserResDto(userIdx);

            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostUserResDto logIn(UserReqDto userReqDto) throws BaseException {
        User user = userRepository.findByEmail(userReqDto.getEmail());

        if (bCryptPasswordEncoder.matches(userReqDto.getPassword(), user.getPassword())){
            int userIdx = user.getUserIdx();
            return new PostUserResDto(userIdx);
        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    public void updateUser(@PathVariable int userId, @RequestBody UserReqDto userReqDto) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(MODIFY_FAIL_USERNAME));

        if (userReqDto.getEmail() != null) {
            user.setEmail(userReqDto.getEmail());
        }
        if (userReqDto.getNickname() != null) {
            user.setNickname(userReqDto.getNickname());
        }
        if (userReqDto.getPassword() != null) {
            String rawPassword = user.getPassword();
            String encPassword = bCryptPasswordEncoder.encode(rawPassword);
            user.setPassword(encPassword);
        }


        userRepository.save(user);
    }

    public UserResDto getUser(@PathVariable int userId) throws BaseException {


        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(USERS_EMPTY_USER_ID));

        return new UserResDto(user);
    }


//    public ResponseEntity<Map<String, Boolean>> deleteUser(int userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ApiRequestException(String.format("해당 사용자가 없습니다.", userId)));
//
//        userRepository.delete(user);
//        Map <String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return ResponseEntity.ok(response);
//    }

}
