package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platforms")
public class Platform {

    @Id
    private Long id;

    private String abbreviation;

    private int generation;
    private String summary;

    @Column(name = "alternative_name")
    private String alternativeName;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    private PlatformCategoryType platformCategoryType;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToOne
    @JoinColumn(name = "platform_family")
    private PlatformFamily platformFamily;

    @OneToOne
    @JoinColumn(name = "platform_logo")
    private PlatformLogo platformLogo;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<PlatformVersion> platformVersions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
