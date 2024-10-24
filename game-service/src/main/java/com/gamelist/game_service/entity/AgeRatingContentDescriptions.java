package com.gamelist.game_service.entity;

import com.gamelist.game_service.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "age_rating_content_descriptions")
public class AgeRatingContentDescriptions {

    @Id
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "age_rating_category_type", nullable = false)
    private AgeRatingContentDescriptionType ageRatingCategory;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    // TODO: Many to many
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_rating_id", nullable = false)
    private AgeRating ageRating;
}
