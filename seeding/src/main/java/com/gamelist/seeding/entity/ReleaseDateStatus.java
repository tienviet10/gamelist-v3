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
@Entity(name = "release_date_status")
public class ReleaseDateStatus {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
