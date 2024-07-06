package com.gamelist.game_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
@Entity(name = "tags")
public class Tag {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Game> games;
}
