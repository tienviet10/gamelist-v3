package com.gamelist.game_service.entity;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "game_version_features")
public class GameVersionFeature {

    @Id
    private Long id;

    private String description;

    @Column(nullable = false)
    @OneToMany
    private Set<GameVersionFeatureValue> values = new HashSet<>();

    @Column(name = "category_type")
    private GameVersionFeatureType gameVersionFeatureType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int position;

    @Column(unique = true, nullable = false)
    private UUID checksum;

}
