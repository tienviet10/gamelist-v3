package controllers

import (
	"containerized-go-app/config"
	"containerized-go-app/helpers"
	"context"
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"golang.org/x/crypto/bcrypt"
)

type User struct {
	ID            primitive.ObjectID `bson:"_id,omitempty"`
	Email         string             `bson:"email"`
	Password      string             `bson:"password"`
	BannerPicture string             `bson:"banner_picture"`
	Bio           string             `bson:"bio"`
	IsActivated   bool               `bson:"is_activated"`
	CreatedAt     time.Time          `bson:"created_at"`
	ListsOrder    string             `bson:"listsorder"`
	UpdatedAt     time.Time          `bson:"updated_at"`
	UserPicture   string             `bson:"user_picture"`
	Username      string             `bson:"username"`
}

type UserResponse struct {
	Username      string `json:"username"`
	Email         string `json:"email"`
	BannerPicture string `json:"bannerPicture"`
	UserPicture   string `json:"userPicture"`
}

func createUserResponse(user User) UserResponse {
	return UserResponse{
		Username:      user.Username,
		Email:         user.Email,
		BannerPicture: user.BannerPicture,
		UserPicture:   user.UserPicture,
	}
}

func Register(c *gin.Context) {
	_, traceID := helpers.StartSpanWithTracer(c, "Register")
	log.Printf("Register in user with trace ID: %s", traceID)

	collection := config.MongoClient.Database("gamelist").Collection("users")

	var user User
	if err := c.ShouldBindJSON(&user); err != nil {
		helpers.SendErrorResponse(c, http.StatusBadRequest, "Bad Request", err.Error())
		return
	}

	if user.Username == "" {
		helpers.SendErrorResponse(c, http.StatusBadRequest, "Bad Request", "Username is required")
		return
	}

	if user.Email == "" {
		helpers.SendErrorResponse(c, http.StatusBadRequest, "Bad Request", "Email is required")
		return
	}

	if user.Password == "" {
		helpers.SendErrorResponse(c, http.StatusBadRequest, "Bad Request", "Password is required")
		return
	}

	var result User
	err := collection.FindOne(context.TODO(), bson.M{"email": user.Email}).Decode(&result)
	if err != mongo.ErrNoDocuments {
		if err != nil {
			helpers.SendErrorResponse(c, http.StatusInternalServerError, "Internal Server Error", "Error checking if email exists")
			return
		}
		helpers.SendErrorResponse(c, http.StatusBadRequest, "Bad Request", "Email already exists")
		return
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)
	if err != nil {
		helpers.SendErrorResponse(c, http.StatusInternalServerError, "Internal Server Error", "Error hashing password")
		return
	}

	user.Password = string(hash)
	user.ListsOrder = "planning,playing,completed,paused,dropped,justAdded"
	user.IsActivated = true

	_, err = collection.InsertOne(context.TODO(), user)
	if err != nil {
		helpers.SendErrorResponse(c, http.StatusInternalServerError, "Internal Server Error", "Error registering user")
		return
	}

	token, err := helpers.GenerateToken(user.ID.Hex(), user.Email)
	if err != nil {
		helpers.SendErrorResponse(c, http.StatusInternalServerError, "Internal Server Error", "Error generating token")
		return
	}

	userResponse := createUserResponse(user)
	data := map[string]interface{}{
		"token": token,
		"user":  userResponse,
	}

	helpers.SendSuccessResponse(c, http.StatusOK, "OK", "Registered successfully", "Registered", data)
}

func Login(c *gin.Context) {
	_, traceID := helpers.StartSpanWithTracer(c, "Login")
	log.Printf("Logging in user with trace ID: %s", traceID)
	collection := config.MongoClient.Database("gamelist").Collection("users")

	var user User
	if err := c.ShouldBindJSON(&user); err != nil {
		helpers.SendErrorResponse(c, http.StatusBadRequest, "Bad Request", err.Error())
		return
	}

	var foundUser User
	err := collection.FindOne(context.TODO(), bson.M{"email": user.Email}).Decode(&foundUser)
	if err != nil {
		if err == mongo.ErrNoDocuments {
			helpers.SendErrorResponse(c, http.StatusUnauthorized, "Unauthorized", "Invalid username or password")
		} else {
			helpers.SendErrorResponse(c, http.StatusInternalServerError, "Internal Server Error", "Error logging in")
		}
		return
	}

	err = bcrypt.CompareHashAndPassword([]byte(foundUser.Password), []byte(user.Password))
	if err != nil {
		helpers.SendErrorResponse(c, http.StatusUnauthorized, "Unauthorized", "Invalid username or password")
		return
	}

	token, err := helpers.GenerateToken(foundUser.ID.Hex(), foundUser.Email)
	if err != nil {
		helpers.SendErrorResponse(c, http.StatusInternalServerError, "Internal Server Error", "Error generating token")
		return
	}

	userResponse := createUserResponse(foundUser)
	data := map[string]interface{}{
		"token": token,
		"user":  userResponse,
	}

	helpers.SendSuccessResponse(c, http.StatusOK, "OK", "Logged in successfully", "User logged in", data)
}
