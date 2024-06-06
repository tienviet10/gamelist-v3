package com.gamelist.social_service.repository;

import com.gamelist.social_service.entity.User;
import com.gamelist.social_service.projection.FollowView;
import com.gamelist.social_service.projection.UserBasicView;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"followers", "following"})
    Optional<User> findWithFollowersAndFollowingById(Long id);

    boolean existsInFollowingByIdAndFollowersId(Long id, Long followerId);

    boolean existsInFollowersByIdAndFollowersId(Long id, Long followerId);

    @EntityGraph(attributePaths = {"followers", "following"})
    Optional<FollowView> findFollowViewViewWithFollowersAndFollowingById(Long id);

    Optional<UserBasicView> findBasicViewById(Long id);
}
