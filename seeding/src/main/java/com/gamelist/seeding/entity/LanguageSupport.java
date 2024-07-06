package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "language_supports")
public class LanguageSupport {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @OneToOne
    @JoinColumn(name = "language_support_type_id", nullable = false)
    private LanguageSupportType languageSupportType;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
