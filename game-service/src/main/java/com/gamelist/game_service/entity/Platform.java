package com.gamelist.game_service.entity;

import com.gamelist.game_service.enums.*;
import com.google.gson.annotations.*;
import jakarta.persistence.Index;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

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

    @Column(length = 2000)
    private String summary;

    @Column(name = "alternative_name")
    @SerializedName(value = "alternative_name")
    private String alternativeName;

    @Column(name = "category_type")
    @SerializedName(value = "category_type")
    @Enumerated(EnumType.ORDINAL)
    private PlatformCategoryType platformCategoryType;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToOne
    @JoinColumn(name = "platform_family_id")
    @SerializedName(value = "platform_family_id")
    private PlatformFamily platformFamily;

    @OneToOne
    @JoinColumn(name = "platform_logo_id")
    @SerializedName(value = "platform_logo_id")
    private PlatformLogo platformLogo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "platform_platform_versions",
            joinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "platform_version_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_platform_platform_versions", columnList = "platform_id, platform_version_id", unique = true)
            })
    private Set<PlatformVersion> platformVersions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    @SerializedName(value = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @SerializedName(value = "updated_at")
    private LocalDateTime updatedAt;
}
