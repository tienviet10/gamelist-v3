package com.gamelist.auth_service.service;

public interface JwtService {
    String generateToken(long userId, String email);
}
