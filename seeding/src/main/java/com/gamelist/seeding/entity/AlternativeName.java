package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "alternative_names")
public class AlternativeName {

    @Id
    private Long id;

    private String comment;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;
}
