package com.gamelist.seeding.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    @Field("banner_picture")
    @JsonProperty("banner_picture")
    private String bannerPicture;

    @Field("user_picture")
    @JsonProperty("user_picture")
    private String userPicture;

    private String bio;

    @Field("is_activated")
    @JsonProperty("is_activated")
    private boolean isActivated;

    @Field("listsorder")
    @JsonProperty("listsorder")
    private String listsOrder;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

}
