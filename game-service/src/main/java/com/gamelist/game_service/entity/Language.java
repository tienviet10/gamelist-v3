package com.gamelist.game_service.entity;

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
@Entity(name = "languages")
public class Language {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String locale;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "native_name", unique = true, nullable = false)
    private String nativeName;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
