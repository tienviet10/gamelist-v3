package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_version_release_dates")
public class PlatformVersionReleaseDate {

    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "m")
    private int month;

    @Column(name = "y")
    private int year;

    @Column(nullable = false)
    private String human;

    @Column(name = "category_type", nullable = false)
    private ReleaseDateCategoryType releaseDateCategoryType;

    @ManyToOne
    @JoinColumn(name = "platform_version_id")
    private PlatformVersion platformVersion;

    @OneToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
