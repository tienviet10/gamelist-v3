package main

import (
	"containerized-go-app/config"
	"containerized-go-app/routers"
	"log"
)

func main() {
	if err := config.SetupConfig(); err != nil {
		log.Fatal("Could not setup config")
	}

	masterDBName := config.DbConfiguration()

  if err := config.ConnectToMongoDB(masterDBName); err != nil {
		log.Fatal("Could not connect to MongoDB")
	}

	router := routers.SetupRoute()

	log.Println(router.Run(config.ServerConfig()))
}

