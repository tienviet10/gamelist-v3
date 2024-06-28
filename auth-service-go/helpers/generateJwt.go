package helpers

import (
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/spf13/viper"
)

func GenerateToken(userId string, email string) (string, error) {
	var jwtKey = []byte(viper.GetString("VAR_JWT_SECRET"))
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
