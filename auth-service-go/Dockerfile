FROM golang:1.22-alpine as builder

RUN apk update && apk add --no-cache git

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

COPY . .

ARG VAR_MONGO_DB
ARG VAR_JWT_SECRET

ENV VAR_MONGO_DB=$VAR_MONGO_DB
ENV VAR_JWT_SECRET=$VAR_JWT_SECRET

RUN echo "VAR_MONGO_DB=${VAR_MONGO_DB}" >> .env && echo "VAR_MONGO_DB=${VAR_MONGO_DB}" >> .env && echo "VAR_JWT_SECRET=${VAR_JWT_SECRET}" >> .env

RUN CGO_ENABLED=0 GOOS=linux go build -mod=readonly -v -o main .

FROM alpine:latest

RUN apk --no-cache add ca-certificates

WORKDIR /root/

COPY --from=builder /app/main .
COPY --from=builder /app/.env .env

EXPOSE 8082

CMD ["./main"]
