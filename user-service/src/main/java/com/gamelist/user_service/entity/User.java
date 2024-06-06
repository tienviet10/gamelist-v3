package com.gamelist.user_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @JsonProperty("email_address")
    private String email;

    @Column(name = "banner_picture")
    @JsonProperty("banner_picture")
    private String bannerPicture;

    @Column(name = "user_picture")
    @JsonProperty("user_picture")
    private String userPicture;

    private String bio;
}
