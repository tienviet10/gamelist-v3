package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
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
    private Set<GameVersionFeatureValue> values;

    @Column(name = "category_type")
    private GameVersionFeatureType gameVersionFeatureType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int position;

    @Column(unique = true, nullable = false)
    private UUID checksum;

}
