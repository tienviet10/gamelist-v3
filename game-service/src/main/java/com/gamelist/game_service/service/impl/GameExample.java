package com.gamelist.game_service.service.impl;

import com.gamelist.game.ExampleServiceGrpc;
import com.gamelist.game.GameInformation;
import com.gamelist.game.GameInformationRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GameExample extends ExampleServiceGrpc.ExampleServiceImplBase {
    @Override
    public void getExampleInformation(
            GameInformationRequest request, StreamObserver<GameInformation> responseObserver) {
        var gameId = request.getGameId();
        var gameInfo = GameInformation.newBuilder().setName("Game " + gameId).build();
        responseObserver.onNext(gameInfo);
        responseObserver.onCompleted();
    }
}
