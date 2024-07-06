package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "websites")
public class Website {

    @Id
    private Long id;

    @Column(nullable = false)
    private boolean trusted;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(name = "category", nullable = false)
    private WebsiteType websiteType;

    @Column(unique = true)
    private UUID checksum;

    // todo
//    @ManyToOne
//    @MapsId
//    private Game game;

}
