package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "platform_version_companies")
public class PlatformVersionCompany {

    @Id
    private Long id;

    private String comment;

    @Column(nullable = false)
    private boolean developer;

    @Column(nullable = false)
    private boolean manufacturer;

    @Column(nullable = false, unique = true)
    private UUID checksum;

    @JoinColumn(nullable = false)
    @OneToOne
    private Company company;
}
