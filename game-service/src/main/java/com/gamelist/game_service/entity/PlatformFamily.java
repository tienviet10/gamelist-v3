package com.gamelist.game_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_families")
public class PlatformFamily {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    @Column(unique = true)
    private UUID checksum;
}