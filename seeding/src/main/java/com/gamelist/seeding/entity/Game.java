package com.gamelist.seeding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "games")
@Table(
        indexes = {
                @Index(name = "nameIndex", columnList = "name"),
                @Index(name = "releaseDateIndex", columnList = "releaseDate"),
                @Index(name = "avgScoreIndex", columnList = "avg_score"),
                @Index(name = "totalRatingIndex", columnList = "total_rating"),
                @Index(name = "mulitIndexNameIdAsc", columnList = "name ASC, id"),
                @Index(name = "mulitIndexNameIdDesc", columnList = "name DESC, id"),
                @Index(name = "mulitIndexReleaseDateIdAsc", columnList = "releaseDate ASC, id"),
                @Index(name = "mulitIndexReleaseDateIdDesc", columnList = "releaseDate DESC, id"),
                @Index(name = "mulitIndexAvgScoreIdAsc", columnList = "avg_score ASC, id"),
                @Index(name = "mulitIndexAvgScoreIdDesc", columnList = "avg_score DESC, id"),
                @Index(name = "mulitIndexTotalRatingIdAsc", columnList = "total_rating ASC, id"),
                @Index(name = "mulitIndexTotalRatingIdDesc", columnList = "total_rating DESC, id"),
        })
public class Game extends InteractiveEntity {

    private String storyline;

    @Column(name = "aggregated_rating_count")
    private int aggregatedRatingCount;

    @Column(name = "aggregated_rating")
    private double aggregatedRating;

    @Column(name = "status")
    private GameStatusType gameTypeStatus;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(name = "category_type", nullable = false)
    private GameCategoryType categoryType;

    @Column(name = "total_rating_count")
    private int totalRatingCount;

    @Column(name = "version_title")
    private String versionTitle;

    @Column(length = 2000)
    private String summary;

    @Column(name = "avg_score")
    private double avgScore;

    @Column(name = "first_release_date")
    private LocalDateTime firstReleaseDate;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_game_id")
    private Game parent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_parent")
    private Game parentVersion;

    @OneToOne
    private Cover cover;

    @ManyToOne(fetch = FetchType.LAZY)
    private Franchise franchise;

    // One To Many

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Franchise> franchises = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Artwork> artworks = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AlternativeName> alternativeNames = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GameEngine> gameEngines = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GameMode> gameModes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PlayerPerspective> playerPerspectives = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GameLocalization> gameLocalization = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReleaseDate> releaseDates = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<InvolvedCompany> involvedCompanies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Keyword> keywords = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<LanguageSupport> languageSupports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MultiplayerMode> multiplayerModes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Website> websites = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_ports",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_port_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_ports", columnList = "game_id, game_port_id", unique = true)
            })
    private Set<Game> ports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_remakes",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_remake_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_remakes", columnList = "game_id, game_remake_id", unique = true)
            })
    private Set<Game> remakes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_remasters",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_remaster_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_remasters", columnList = "game_id, game_remake_id", unique = true)
            })
    private Set<Game> remasters = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "similar_games",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "similar_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_similar_games", columnList = "game_id, similar_game_id", unique = true)
            })
    private Set<Game> similarGames = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_bundles",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bundle_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_bundles", columnList = "game_id, bundle_id", unique = true)
            })
    private Set<Game> bundles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_dlc",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_dlc_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_dlc", columnList = "game_id, game_dlc_id", unique = true)
            })
    private Set<Game> dlc = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_expanded_games",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "expanded_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_expanded_games", columnList = "game_id, expanded_game_id", unique = true)
            })
    private Set<Game> expandedGames = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_forks",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "forked_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_forks", columnList = "game_id, forked_game_id", unique = true)
            })
    private Set<Game> forks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_expansions",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "expansion_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_expansions", columnList = "game_id, expansion_game_id", unique = true)
            })
    private Set<Game> expansions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "standalone_game_expansions",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "standalone_game_expansion_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_standalone_game_expansions", columnList = "game_id, standalone_game_expansion_id", unique = true)
            })
    private Set<Game> standaloneExpansions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("game")
    @Column(name = "user_games")
    private Set<UserGame> userGames = new HashSet<>();

    // Many to Many

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Screenshot> screenshots = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_age_ratings",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "age_rating_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_age_ratings", columnList = "game_id, age_rating_id", unique = true)
            })
    private Set<AgeRating> ageRatings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_genres",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"),
            indexes = {
                    @Index(name = "game_genres_gameId", columnList = "game_id"),
                    @Index(name = "game_genres_genreId", columnList = "genre_id")
            })
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_platforms",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id"),
            indexes = {
                    @Index(name = "game_platforms_gameId", columnList = "game_id"),
                    @Index(name = "game_platforms_platformId", columnList = "platform_id")
            })
    private Set<Platform> platforms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_tags",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            indexes = {
                    @Index(name = "game_tags_gameId", columnList = "game_id"),
                    @Index(name = "game_tags_tagId", columnList = "tag_id")
            })
    private Set<Tag> tags = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
