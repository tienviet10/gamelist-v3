package com.gamelist.seeding.entity;

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
@Entity(name = "events")
public class Event {

    @Id
    private Long id;

    private String description;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "event_logo")
    private EventLogo eventLogo;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_networks",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "network_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_event_networks", columnList = "event_id, network_id", unique = true)
            })
    private Set<Game> eventNetworks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_games",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_event_games", columnList = "company_id, game_id", unique = true)
            })
    private Set<Game> games = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
