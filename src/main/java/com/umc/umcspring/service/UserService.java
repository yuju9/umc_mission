package com.umc.umcspring.service;

import com.umc.umcspring.handler.ApiRequestException;
import com.umc.umcspring.model.User;
import com.umc.umcspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User userUpdate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(String.format("사용자가 존재하지 않습니다.")));

        user.setAddress(userUpdate.getAddress());
        user.setNickname(userUpdate.getNickname());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<Map<String, Boolean>> deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(String.format("해당 사용자가 없습니다.", userId)));

        userRepository.delete(user);
        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
