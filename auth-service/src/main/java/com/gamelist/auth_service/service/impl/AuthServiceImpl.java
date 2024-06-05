package com.gamelist.auth_service.service.impl;

import com.gamelist.auth_service.entity.Role;
import com.gamelist.auth_service.entity.User;
import com.gamelist.auth_service.repository.UserRepository;
import com.gamelist.auth_service.service.AuthService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> attemptLogin(String email, String password) {
        var authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User principal = (User) authentication.getPrincipal();
        return formReturnedData(principal);
    }

    public Map<String, Object> attemptSignup(String email, String password, String username) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setListsOrder("planning,playing,completed,paused,dropped,justAdded");
        user.setActive(true);

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        var savedUser = userRepository.save(user);
        return formReturnedData(savedUser);
    }

    private Map<String, Object> formReturnedData(User user) {
        var roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Map<String, Object> returnedData = new HashMap<>();
        returnedData.put("token", jwtService.generateToken(user.getId(), user.getEmail(), roles));

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("username", user.getUsername());
        userMap.put("bannerPicture", user.getBannerPicture() != null ? user.getBannerPicture() : "");
        userMap.put("userPicture", user.getUserPicture() != null ? user.getUserPicture() : "");

        returnedData.put("user", userMap);

        return returnedData;
    }
}
