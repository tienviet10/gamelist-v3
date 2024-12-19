package controllers

import (
	"context"
	"log"

	game "github.com/gamelist/game/pb"
	"google.golang.org/grpc"
)

type ExampleService interface {
	GetExampleInformation(ctx context.Context) int
}

type ExampleGrpcHandler struct {
	exampleService ExampleService
	game.UnimplementedExampleServiceServer
}

func NewGrpcExampleService(grpc *grpc.Server, exampleService ExampleService) {
	gRPCHandler := &ExampleGrpcHandler{
		exampleService: exampleService,
	}

	// register the OrderServiceServer
	game.RegisterExampleServiceServer(grpc, gRPCHandler)
}

func (h *ExampleGrpcHandler) GetExampleInformation(ctx context.Context, req *game.GameInformationRequest) (*game.GameInformation, error) {
	h.exampleService.GetExampleInformation(ctx)
	res := &game.GameInformation{
		Name: "Game 12343245",
	}

	log.Printf("Response.......: %v", res, req.GameId)
	return res, nil
}
