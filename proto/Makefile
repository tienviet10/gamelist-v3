proto:
	rm -f pb/*.go pb/common/*.go
	protoc --proto_path=src/main/proto --go_out=pb --go_opt=paths=source_relative \
	--go-grpc_out=pb --go-grpc_opt=paths=source_relative \
	--grpc-gateway_out=pb --grpc-gateway_opt=paths=source_relative \
	src/main/proto/*.proto src/main/proto/common/*.proto

.PHONY: proto