package com.gamelist.game_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.*;
import org.springframework.scheduling.annotation.*;

@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
public class GameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServiceApplication.class, args);
    }
}
