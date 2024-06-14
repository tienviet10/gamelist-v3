package main

import (
	"containerized-go-app/config"
	"containerized-go-app/routers"
	"context"
	"flag"
	"log"
	"os"
	"os/signal"

	"go.opentelemetry.io/otel"
	"go.opentelemetry.io/otel/exporters/zipkin"
	"go.opentelemetry.io/otel/sdk/resource"
	sdktrace "go.opentelemetry.io/otel/sdk/trace"
	semconv "go.opentelemetry.io/otel/semconv/v1.25.0"
)

var logger = log.New(os.Stderr, "zipkin-auth", log.Ldate|log.Ltime|log.Llongfile)

func initTracer(url string) (func(context.Context) error, error) {
	exporter, err := zipkin.New(
		url,
		zipkin.WithLogger(logger),
	)
	if err != nil {
		return nil, err
	}

	batcher := sdktrace.NewBatchSpanProcessor(exporter)

	tp := sdktrace.NewTracerProvider(
		sdktrace.WithSpanProcessor(batcher),
		sdktrace.WithResource(resource.NewWithAttributes(
			semconv.SchemaURL,
			semconv.ServiceName("auth-service"),
		)),
	)
	otel.SetTracerProvider(tp)

	return tp.Shutdown, nil
}

func main() {
	url := flag.String("zipkin", "http://tempo:9411/api/v2/spans", "zipkin url")
	flag.Parse()

	ctx, cancel := signal.NotifyContext(context.Background(), os.Interrupt)
	defer cancel()

	shutdown, err := initTracer(*url)
	if err != nil {
		log.Fatal(err)
	}
	defer func() {
		if err := shutdown(ctx); err != nil {
			log.Fatal("failed to shutdown TracerProvider: %w", err)
		}
	}()

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
