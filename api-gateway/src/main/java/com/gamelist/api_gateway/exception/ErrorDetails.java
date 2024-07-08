package com.gamelist.api_gateway.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String details) {}
