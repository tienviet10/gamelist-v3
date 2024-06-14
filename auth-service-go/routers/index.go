package routers

import (
	"containerized-go-app/controllers"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/prometheus/client_golang/prometheus"
	"github.com/prometheus/client_golang/prometheus/promauto"
	"github.com/prometheus/client_golang/prometheus/promhttp"
)

var (
	httpRequestsTotal = promauto.NewCounterVec(
		prometheus.CounterOpts{
			Name: "http_server_requests_seconds_count",
			Help: "Total number of HTTP requests",
		},
		[]string{"application", "method", "status", "uri"},
	)
)

func RegisterRoutes(route *gin.Engine) {
	route.Use(func(ctx *gin.Context) {
		ctx.Next()
		httpRequestsTotal.With(prometheus.Labels{
			"application": "auth-service",
			"method":      ctx.Request.Method,
			"status":      strconv.Itoa(ctx.Writer.Status()),
			"uri":         ctx.Request.RequestURI,
		}).Inc()
	})

	route.NoRoute(func(ctx *gin.Context) {
		ctx.JSON(http.StatusNotFound, gin.H{"status": http.StatusNotFound, "message": "Route Not Found"})
	})
	route.POST("/register", controllers.Register)
	route.POST("/login", controllers.Login)
	route.GET("/actuator/prometheus", gin.WrapH(promhttp.Handler()))
}
