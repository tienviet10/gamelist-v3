package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "company_websites")
public class CompanyWebsite {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private boolean trusted;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category_type", nullable = false, unique = true)
    private WebsiteType companyWebsiteType;
}
