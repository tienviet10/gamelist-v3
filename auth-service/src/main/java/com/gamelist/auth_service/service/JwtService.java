package com.gamelist.auth_service.service;

import java.util.List;

public interface JwtService {
    String generateToken(long userId, String email, List<String> roles);
}
