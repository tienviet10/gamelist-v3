package com.gamelist.game_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@PrimaryKeyJoinColumn(name = "game_journal_id")
@NoArgsConstructor
@Entity(name = "game_journals")
public class GameJournal extends InteractiveEntity {

    private String content;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JsonIgnoreProperties("game_journals")
    //    @JoinColumn(name = "user_id", referencedColumnName = "id")
    //    private User user;
    @Column(name = "user_id")
    private String userId;
}
