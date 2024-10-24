package com.gamelist.game_service.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "game_journal_id")
@NoArgsConstructor
@Entity(name = "game_journals")
public class GameJournal extends InteractiveEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("game_journals")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
