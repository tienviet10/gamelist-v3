package com.gamelist.auth_service.service.impl;


import com.gamelist.auth_service.entity.User;
import com.gamelist.auth_service.repository.UserRepository;
import com.gamelist.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;

    public Map<String, Object> attemptSignup(String email, String password, String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setListsOrder("planning,playing,completed,paused,dropped,justAdded");
        user.setActive(true);

        var savedUser = userRepository.save(user);
        return formReturnedData(savedUser);
    }

    private Map<String, Object> formReturnedData(User user) {
        Map<String, Object> returnedData = new HashMap<>();
        returnedData.put("token", jwtService.generateToken(user.getId(), user.getEmail()));

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("username", user.getUsername());
        userMap.put("bannerPicture", user.getBannerPicture() != null ? user.getBannerPicture() : "");
        userMap.put("userPicture", user.getUserPicture() != null ? user.getUserPicture() : "");

        returnedData.put("user", userMap);

        return returnedData;
    }
}
