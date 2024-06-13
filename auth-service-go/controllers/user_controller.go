package controllers

import (
	"containerized-go-app/config"
	"context"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
	"github.com/spf13/viper"
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
	ListsOrder    []string           `bson:"listsorder"`
	UpdatedAt     time.Time          `bson:"updated_at"`
	UserPicture   string             `bson:"user_picture"`
	Username      string             `bson:"username"`
}

func Register(c *gin.Context) {
	collection := config.MongoClient.Database("gamelist").Collection("users")

	var user User
	if err := c.ShouldBindJSON(&user); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Error hashing password"})
		return
	}

	user.Password = string(hash)

	_, err = collection.InsertOne(context.TODO(), user)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Error registering user"})
		return
	}

	token, err := generateToken(1, user.Email)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Error generating token"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "User registered successfully", "token": token})
}

func Login(c *gin.Context) {
	collection := config.MongoClient.Database("gamelist").Collection("users")

	var user User
	if err := c.ShouldBindJSON(&user); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	var foundUser User
	err := collection.FindOne(context.TODO(), bson.M{"email": user.Email}).Decode(&foundUser)
	if err != nil {
		if err == mongo.ErrNoDocuments {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid username or password"})
		} else {
			c.JSON(http.StatusInternalServerError, gin.H{"error": "Error logging in"})
		}
		return
	}

	err = bcrypt.CompareHashAndPassword([]byte(foundUser.Password), []byte(user.Password))
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid username or password"})
		return
	}

	token, err := generateToken(1, foundUser.Email)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Error generating token"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Logged in successfully", "token": token})
}

func generateToken(userId int, email string) (string, error) {
	var jwtKey = []byte(viper.GetString("JWT_SECRET"))
	claims := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"sub":   userId,
		"iss":   "gamelist-auth-service",
		"exp":   time.Now().Add(time.Hour).Unix(),
		"iat":   time.Now().Unix(),
		"email": email,
	})

	tokenString, err := claims.SignedString(jwtKey)
	if err != nil {
		return "", err
	}
	return tokenString, nil
}
