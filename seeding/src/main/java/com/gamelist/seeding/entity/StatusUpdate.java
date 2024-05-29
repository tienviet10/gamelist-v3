package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "status_updates")
@PrimaryKeyJoinColumn(name = "status_update_id")
public class StatusUpdate extends InteractiveEntity {

    @Column(name = "game_status")
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_game_id", referencedColumnName = "id")
    private UserGame userGame;
}
