package main

import (
	"containerized-go-app/service"
	"log"
	"net"

	"containerized-go-app/controllers"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

type gRPCServer struct {
	addr string
}

func NewGRPCServer(addr string) *gRPCServer {
	return &gRPCServer{addr: addr}
}

func (s *gRPCServer) Run() error {
	lis, err := net.Listen("tcp", s.addr)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	grpcServer := grpc.NewServer()
	reflection.Register(grpcServer)

	// register our grpc services
	exampleService := service.NewExampleService()
	controllers.NewGrpcExampleService(grpcServer, exampleService)

	log.Println("Starting gRPC server on", s.addr)

	return grpcServer.Serve(lis)
}
