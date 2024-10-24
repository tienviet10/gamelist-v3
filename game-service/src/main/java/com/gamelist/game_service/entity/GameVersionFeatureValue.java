package com.gamelist.game_service.entity;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "game_version_feature_values")
public class GameVersionFeatureValue {

    @Id
    private Long id;

    private String note;

    @Column(name = "included_feature_type")
    private IncludedFeatureType includedFeatureType;

    @ManyToOne
    @JoinColumn(name = "game_feature_id", nullable = false)
    private GameVersionFeature gameVersionFeature;

    @OneToOne
    @JoinColumn(nullable = false)
    private Game game;

    @Column(unique = true, nullable = false)
    private UUID checksum;
}
