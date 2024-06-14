package helpers

import (
	"context"

	"github.com/gin-gonic/gin"
	"go.opentelemetry.io/otel"
	"go.opentelemetry.io/otel/propagation"
)

func StartSpanWithTracer(c *gin.Context, operation string) (context.Context, string) {
	tp := otel.GetTracerProvider()
	tracer := tp.Tracer("auth-service")

	propagator := propagation.TraceContext{}
	carrier := propagation.HeaderCarrier(c.Request.Header)
	ctx := propagator.Extract(c.Request.Context(), carrier)

	ctx, span := tracer.Start(ctx, operation)
	defer span.End()

	return ctx, span.SpanContext().TraceID().String()
}
