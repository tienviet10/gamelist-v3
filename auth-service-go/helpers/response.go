package helpers

import (
	"time"

	"github.com/gin-gonic/gin"
)

type HttpResponse struct {
	TimeStamp        string                 `json:"timestamp"`
	StatusCode       int                    `json:"status_code"`
	Status           string                 `json:"status"`
	Message          string                 `json:"message"`
	DeveloperMessage string                 `json:"developer_message"`
	Path             string                 `json:"path"`
	RequestMethod    string                 `json:"request_method"`
	Data             map[string]interface{} `json:"data"`
}

type ErrorDetails struct {
	Timestamp time.Time `json:"timestamp"`
	Message   string    `json:"message"`
	Details   string    `json:"details"`
}

func SendSuccessResponse(c *gin.Context, statusCode int, status string, message string, developerMessage string, data map[string]interface{}) {
	response := HttpResponse{
		TimeStamp:        time.Now().Format(time.RFC3339),
		StatusCode:       statusCode,
		Status:           status,
		Message:          message,
		DeveloperMessage: developerMessage,
		Path:             c.Request.URL.Path,
		RequestMethod:    c.Request.Method,
		Data:             data,
	}

	c.JSON(statusCode, response)
}

func SendErrorResponse(c *gin.Context, statusCode int, message string, details string) {
	errDetails := ErrorDetails{
		Timestamp: time.Now(),
		Message:   message,
		Details:   details,
	}
	c.JSON(statusCode, errDetails)
}
