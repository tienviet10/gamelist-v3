package com.gamelist.game_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platforms")
public class Platform {

    @Id
    private Long id;

    @Column
    private String abbreviation;

    @Column
    private String alternative_name;

    @Column(unique = true)
    private UUID checksum;

    @OneToOne
    private PlatformCategory platformCategory;

    @Column
    private int generation;

    @Column
    private String summary;

    @Column(unique = true)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // TODO: Do we actually want this to be FetchType lazy? Aggressive might cost a little more memory but could speed it up
    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    private Set<Game> games;
}
