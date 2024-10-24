package com.gamelist.game_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "status_updates")
@PrimaryKeyJoinColumn(name = "status_update_id")
public class StatusUpdate extends InteractiveEntity {

    @Column(name = "game_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_game_id", referencedColumnName = "id", nullable = false)
    private UserGame userGame;
}
