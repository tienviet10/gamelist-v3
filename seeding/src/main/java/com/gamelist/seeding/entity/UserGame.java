package com.gamelist.seeding.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import java.time.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "user_games")
public class UserGame {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "game_status")
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "completed_date")
    private LocalDateTime completedDate;

    @Column(name = "private")
    private Boolean isPrivate;

    private Integer rating;

    @Column(name = "game_note")
    private String gameNote;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;
}
