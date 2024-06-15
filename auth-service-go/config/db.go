package config

import (
	"context"
	"time"

	"github.com/spf13/viper"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type DatabaseConfiguration struct {
	URL string
}

func DbConfiguration() string {
	// dbName := viper.GetString("MONGO_DB_NAME")
	// dbPass := viper.GetString("MONGO_DB_PASS")

	// connectionString := fmt.Sprintf("mongodb+srv://%s:%s@cluster0.sp1v8.mongodb.net/?retryWrites=true&w=majority", dbName, dbPass)

	connectionString := viper.GetString("VAR_MONGO_DB")

	return connectionString
}

var MongoClient *mongo.Client

func ConnectToMongoDB(masterDBName string) error {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(masterDBName))
	if err != nil {
		return err
	}

	// Check the connection
	err = client.Ping(ctx, nil)
	if err != nil {
		return err
	}

	MongoClient = client
	return nil
}
