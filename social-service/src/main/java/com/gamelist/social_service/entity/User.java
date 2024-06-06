package com.gamelist.social_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password_digest")
    private String password;

    @Column(name = "banner_picture")
    @JsonProperty("banner_picture")
    private String bannerPicture;

    @Column(name = "user_picture")
    @JsonProperty("user_picture")
    private String userPicture;

    private String bio;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "`listsOrder`")
    private String listsOrder;

    //    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    //    @JsonProperty("roles")
    //    @Enumerated(EnumType.STRING)
    //    @RoleSubset(anyOf = {Role.ROLE_USER, Role.ROLE_ADMIN})
    //    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

    //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //    @JsonIgnore
    //    private List<GameJournal> gameJournals;

    //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //    @JsonIgnore
    //    private Set<UserGame> userGames;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}),
            indexes = @Index(columnList = "follower_id, following_id", unique = true))
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();

    public void addFollower(User user) {
        followers.add(user);
        user.getFollowing().add(this);
    }

    public void removeFollower(User user) {
        followers.remove(user);
        user.getFollowing().remove(this);
    }

    public void addFollowing(User user) {
        following.add(user);
        user.getFollowers().add(this);
    }

    public void removeFollowing(User user) {
        following.remove(user);
        user.getFollowers().remove(this);
    }
}
