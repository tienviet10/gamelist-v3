package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "companies")
public class Company {

    @Id
    private Long id;

    private int country;
    private String description;
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(name = "start_date_category", nullable = false)
    private CompanyDateCategory startDateCategory;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "change_date_category", nullable = false)
    private CompanyDateCategory changeDateCategory;

    @OneToOne
    private Company parent;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "changed_company_id")
    private Company changedCompany;

    @OneToOne
    @JoinColumn(name = "company_logo_id")
    private CompanyLogo companyLogo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "companies_game_engines",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_engine_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_company_game_engine", columnList = "company_id, game_engine_id", unique = true)
            })
    private Set<GameEngine> gameEngines = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "companies_developed_games",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_companies_developed_games", columnList = "company_id, game_id", unique = true)
            })
    private Set<Game> developed = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "companies_published_games",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_companies_published_games", columnList = "company_id, game_id", unique = true)
            })
    private Set<Game> published = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
