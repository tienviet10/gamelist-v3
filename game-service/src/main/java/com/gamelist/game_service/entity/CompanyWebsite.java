package com.gamelist.game_service.entity;

import com.gamelist.game_service.enums.*;
import com.google.gson.annotations.*;
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

    @Column(nullable = false)
    private boolean trusted;

    @Column(nullable = false)
    private String url;

    @SerializedName(value = "category")
    @Column(name = "category_type", nullable = false)
    private WebsiteType companyWebsiteType;

    @Column(unique = true, nullable = false)
    private UUID checksum;
}
