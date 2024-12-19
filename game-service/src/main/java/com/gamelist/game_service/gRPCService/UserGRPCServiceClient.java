package com.gamelist.game_service.gRPCService;

import com.gamelist.game.UserCategoryList;
import com.gamelist.game.UserIdRequest;
import com.gamelist.game.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserGRPCServiceClient {
    @GrpcClient("user-service-dotnet")
    private UserServiceGrpc.UserServiceBlockingStub userServiceClient;

    public UserCategoryList getUserCategoryListsInfoById(String userId) {
        var request = UserIdRequest.newBuilder().setUserId(userId).build();
        return this.userServiceClient.getUserCategoryListsInfoById(request);
    }
}
