package com.gamelist.social_service.service.impl;

import com.gamelist.game.UserInfoGRPCResponse;
import com.gamelist.social_service.clients.user.UserDTO;
import com.gamelist.social_service.gRPCService.UserGRPCServiceClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserGRPCServiceClient userGRPCServiceClient;

    public UserDTO fetchUserDetails(String userId, String authorizationHeader, Map<String, UserDTO> userInfoCache) {
        if (userInfoCache.containsKey(userId)) {
            return userInfoCache.get(userId);
        } else {
            UserInfoGRPCResponse tempUserInfo = userGRPCServiceClient.getShortUserInfo(userId);
            if (tempUserInfo == null) {
                throw new RuntimeException("User not found");
            }

            UserDTO saveUserDTO = new UserDTO(
                    tempUserInfo.getUsername(), tempUserInfo.getBannerPicture(), tempUserInfo.getUserPicture(), userId);
            userInfoCache.put(userId, saveUserDTO);
            return saveUserDTO;
        }
    }
}
