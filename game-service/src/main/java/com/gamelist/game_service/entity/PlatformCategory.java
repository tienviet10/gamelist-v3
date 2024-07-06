package com.gamelist.game_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_categories")
public class PlatformCategory {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;
}
