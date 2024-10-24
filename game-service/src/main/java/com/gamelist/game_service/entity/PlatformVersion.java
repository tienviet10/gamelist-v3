package com.gamelist.game_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_versions")
public class PlatformVersion {

    @Id
    private Long id;

    private String connectivity;
    private String cpu;
    private String graphics;
    private String media;
    private String memory;
    private String os;
    private String output;
    private String resolutions;
    private String sound;
    private String storage;
    private String summary;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, unique = true)
    private UUID checksum;

    @OneToOne
    @JoinColumn(name = "main_manufacturer")
    private PlatformVersionCompany mainManufacturer;

    @OneToOne
    @JoinColumn(name = "platform_logo")
    private PlatformLogo platformLogo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "platform_version_platform_version_release_dates",
            joinColumns = @JoinColumn(name = "platform_version_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "platform_version_release_date_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_platform_version_platform_release_dates", columnList = "platform_version_id, platform_version_release_date_id", unique = true)
            })
    private Set<PlatformVersionReleaseDate> platformVersionReleaseDates = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "platform_version_platform_version_companies",
            joinColumns = @JoinColumn(name = "platform_version_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "platform_version_company_id", referencedColumnName = "id", nullable = false),
            indexes = {
                    @Index(name = "idx_platform_version_platform_version_company", columnList = "platform_version_id, platform_version_company_id", unique = true)
            })
    private Set<PlatformVersionCompany> companies = new HashSet<>();
}
