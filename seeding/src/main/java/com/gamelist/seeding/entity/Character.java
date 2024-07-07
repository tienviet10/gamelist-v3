package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "characters")
public class Character {

    @Id
    private Long id;

    private String description;

    @Column(name = "country_name")
    private String countryName;

    @ElementCollection
    @Column(name = "akas")
    @JoinTable(joinColumns = @JoinColumn(name = "character_id", nullable = false))
    private Set<String> aliases;

    @Column(name = "gender_type")
    @Enumerated(EnumType.ORDINAL)
    private GenderType genderType;

    @Column(name = "species_type")
    @Enumerated(EnumType.ORDINAL)
    private CharacterSpeciesType characterSpeciesType;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "characters_games",
            joinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_characters_games", columnList = "character_id, game_id", unique = true)
            })
    private Set<Game> games = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "character_mug_shot_id", nullable = false)
    private CharacterMugShot characterMugShot;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
