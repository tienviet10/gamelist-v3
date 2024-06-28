package com.gamelist.seeding.entity;

import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @Column(name = "user_id")
    private String userId;
}
