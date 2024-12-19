package com.gamelist.social_service.service.impl;

import com.gamelist.game.ExampleServiceGrpc;
import com.gamelist.game.GameInformation;
import com.gamelist.game.GameInformationRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ExampleTwoClient {
    @GrpcClient("auth-service-go")
    private ExampleServiceGrpc.ExampleServiceBlockingStub exampleClient;

    public GameInformation getGameTwoInformation(int gameId) {
        var request = GameInformationRequest.newBuilder().setGameId(gameId).build();
        return this.exampleClient.getExampleInformation(request);
    }
}
