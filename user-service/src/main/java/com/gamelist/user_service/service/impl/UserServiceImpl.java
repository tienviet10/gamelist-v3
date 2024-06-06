package com.gamelist.user_service.service.impl;

import com.gamelist.user_service.entity.User;
import com.gamelist.user_service.exception.UserNotFoundException;
import com.gamelist.user_service.repository.UserRepository;
import com.gamelist.user_service.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public Map<String, Object> getUser(long id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found. Id: " + id));

        return Map.of(
                "email", user.getEmail(),
                "username", user.getUsername(),
                "bannerPicture", user.getBannerPicture(),
                "userPicture", user.getUserPicture());
    }
}
