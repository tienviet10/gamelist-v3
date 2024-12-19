package service

import (
	"context"
	"log"
)

type ExampleService struct {
	// store
}

func NewExampleService() *ExampleService {
	return &ExampleService{}
}

func (s *ExampleService) GetExampleInformation(ctx context.Context) int {
	log.Println("GetTheExample...")
	return 222
}
