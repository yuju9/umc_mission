package com.umc.umcspring.service;

import com.umc.umcspring.handler.BaseException;
import com.umc.umcspring.model.User;
import com.umc.umcspring.repository.UserRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.umc.umcspring.handler.BaseResponseStatus.DATABASE_ERROR;

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

    public User createUser(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        return userRepository.save(user);
    }

    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User userUpdate) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(DATABASE_ERROR));

        if (userUpdate.getEmail() != null) {
            user.setEmail(userUpdate.getEmail());
        }
        if (userUpdate.getNickname() != null) {
            user.setNickname(userUpdate.getNickname());
        }
        if (userUpdate.getPassword() != null) {
            String rawPassword = user.getPassword();
            String encPassword = bCryptPasswordEncoder.encode(rawPassword);
            user.setPassword(encPassword);
        }


        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public User getUser(@PathVariable int userId) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(DATABASE_ERROR));

        return user;
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
