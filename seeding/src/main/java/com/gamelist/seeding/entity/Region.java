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
@Entity(name = "regions")
public class Region {

    @Id
    private Long id;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String category;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
