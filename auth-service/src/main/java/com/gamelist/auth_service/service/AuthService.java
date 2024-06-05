package com.gamelist.auth_service.service;

import java.util.Map;

public interface AuthService {
    Map<String, Object> attemptLogin(String email, String password);

    Map<String, Object> attemptSignup(String email, String password, String username);
}
