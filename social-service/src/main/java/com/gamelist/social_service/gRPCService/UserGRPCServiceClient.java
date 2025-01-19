package com.gamelist.social_service.gRPCService;

import com.gamelist.game.UserExistResponse;
import com.gamelist.game.UserIdRequest;
import com.gamelist.game.UserInfoGRPCResponse;
import com.gamelist.game.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserGRPCServiceClient {
    @GrpcClient("user-service-dotnet")
    private UserServiceGrpc.UserServiceBlockingStub userServiceClient;

    public UserExistResponse checkUserExist(String userId) {
        var request = UserIdRequest.newBuilder().setUserId(userId).build();
        return this.userServiceClient.checkUserExist(request);
    }

    public UserInfoGRPCResponse getShortUserInfo(String userId) {
        var request = UserIdRequest.newBuilder().setUserId(userId).build();
        return this.userServiceClient.getShortUserInfoById(request);
    }
}
