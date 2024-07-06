package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_websites")
public class PlatformWebsite {

    @Id
    private Long id;

    @Column(nullable = false)
    private boolean trusted;

    @Column(nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private UUID checksum;

}
