package com.gamelist.seeding.entity;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PlatformVersionCompany> companies = new HashSet<>();

    @JoinColumn(name = "platform_logo")
    @OneToOne
    private PlatformLogo platformLogo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PlatformVersionReleaseDate> platformVersionReleaseDates = new HashSet<>();

}
