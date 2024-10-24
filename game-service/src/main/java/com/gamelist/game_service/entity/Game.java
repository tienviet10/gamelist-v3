package com.gamelist.game_service.entity;

import com.fasterxml.jackson.annotation.*;
import com.gamelist.game_service.enums.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
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

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Artwork> artworks = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AlternativeName> alternativeNames = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GameLocalization> gameLocalization = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("game")
    @Column(name = "user_games")
    private Set<UserGame> userGames = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "games_franchises",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "franchise_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_franchises", columnList = "game_id, franchise_id", unique = true)
            })
    private Set<Franchise> franchises = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_game_engine",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_engine_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_game_engine", columnList = "game_id, game_engine_id", unique = true)
            })
    private Set<GameEngine> gameEngines = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_game_mode",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_mode_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_game_mode", columnList = "game_id, game_mode_id", unique = true)
            })
    private Set<GameMode> gameModes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_player_perspectives",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "player_perspective_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_player_perspectives", columnList = "game_id, player_perspective_id", unique = true)
            })
    private Set<PlayerPerspective> playerPerspectives = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_release_dates",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "release_date_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_release_dates", columnList = "game_id, release_date_id", unique = true)
            })
    private Set<ReleaseDate> releaseDates = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_involved_companies",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "involved_company_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_involved_companies", columnList = "game_id, involved_company_id", unique = true)
            })
    private Set<InvolvedCompany> involvedCompanies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_keywords",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "keyword_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_keywords", columnList = "game_id, keyword_id", unique = true)
            })
    private Set<Keyword> keywords = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_language_supports",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "language_support_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_language_supports", columnList = "game_id, language_support_id", unique = true)
            })
    private Set<LanguageSupport> languageSupports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_multiplayer_mods",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "multiplayer_mode_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_games_multiplayer_mods", columnList = "game_id, multiplayer_mode_id", unique = true)
            })
    private Set<MultiplayerMode> multiplayerModes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_websites",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "website_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_websites", columnList = "game_id, website_id", unique = true)
            })
    private Set<Website> websites = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_ports",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "ported_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_ports", columnList = "game_id, ported_game_id", unique = true)
            })
    private Set<Game> ports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_remakes",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "remake_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_remakes", columnList = "game_id, remake_game_id", unique = true)
            })
    private Set<Game> remakes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_remasters",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "remaster_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_remasters", columnList = "game_id, remaster_game_id", unique = true)
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
            inverseJoinColumns = @JoinColumn(name = "bundle_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_bundles", columnList = "game_id, bundle_game_id", unique = true)
            })
    private Set<Game> bundles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_dlc",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "dlc_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_dlc", columnList = "game_id, dlc_game_id", unique = true)
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
            inverseJoinColumns = @JoinColumn(name = "standalone_expansion_game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_standalone_game_expansions", columnList = "game_id, standalone_expansion_game_id", unique = true)
            })
    private Set<Game> standaloneExpansions = new HashSet<>();

    // Many to Many

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_screenshots",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "screenshot_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_game_screenshots", columnList = "game_id, screenshot_id", unique = true)
            })
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
                    @Index(name = "idx_games_genres", columnList = "game_id, genre_id", unique = true)
            })
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_platforms",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id"),
            indexes = {
                    @Index(name = "idx_games_platforms", columnList = "game_id, platform_id", unique = true)
            })
    private Set<Platform> platforms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "games_tags",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            indexes = {
                    @Index(name = "idx_games_tags", columnList = "game_id, tag_id", unique = true)
            })
    private Set<Tag> tags = new HashSet<>();
}
