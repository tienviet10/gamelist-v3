package com.gamelist.seeding.entity;

import com.gamelist.seeding.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "age_ratings")
public class AgeRating {

    @Id
    private Long id;

    private String synopsis;

    @Column(name = "rating_cover_url")
    private String ratingCoverUrl;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category", nullable = false)
    private AgeRatingCategoryType ageRatingCategoryType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rating", nullable = false)
    private AgeRatingType ageRatingType;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToMany(mappedBy = "ageRating", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AgeRatingContentDescriptions> descriptions;

    @ManyToMany(mappedBy = "ageRatings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Game> games;
}

