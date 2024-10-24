package com.gamelist.game_service.entity;

import com.google.gson.annotations.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tags")
public class Tag {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Game> games = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    @SerializedName(value = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @SerializedName(value = "updated_at")
    private LocalDateTime updatedAt;
}
