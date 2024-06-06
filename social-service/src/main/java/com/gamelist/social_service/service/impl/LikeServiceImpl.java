package com.gamelist.social_service.service.impl;

import com.gamelist.social_service.entity.*;
import com.gamelist.social_service.exception.InvalidInputException;
import com.gamelist.social_service.exception.ResourceNotFoundException;
import com.gamelist.social_service.projection.LikeEntityView;
import com.gamelist.social_service.repository.InteractiveEntityRepository;
import com.gamelist.social_service.repository.LikeRepository;
import com.gamelist.social_service.repository.UserRepository;
import com.gamelist.social_service.service.LikeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final InteractiveEntityRepository interactiveEntityRepository;

    @Override
    public LikeEntityView createLike(Long userId, Long interactiveEntityId) {
        // Check if the user has already liked the InteractiveEntity
        boolean alreadyLiked = likeRepository.existsByUserIdAndInteractiveEntityId(userId, interactiveEntityId);

        if (alreadyLiked) {
            throw new InvalidInputException("You have already liked this entity.");
        }

        User owner = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LikeEntity like = new LikeEntity();

        Optional<InteractiveEntity> interactiveEntityOptional =
                interactiveEntityRepository.findById(interactiveEntityId);

        like.setUser(owner);

        if (interactiveEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("The entity is not found");
        }

        InteractiveEntity interactiveEntity = interactiveEntityOptional.get();

        if (interactiveEntity instanceof Post
                || interactiveEntity instanceof GameJournal
                || interactiveEntity instanceof Comment
                || interactiveEntity instanceof StatusUpdate
                || interactiveEntity instanceof Game) {
            like.setInteractiveEntity(interactiveEntity);

        } else {
            throw new RuntimeException("Invalid likeable entity");
        }

        like = likeRepository.save(like);

        return likeRepository.findProjectedById(like.getId());
    }

    @Override
    @Transactional
    public void deleteLikeById(Long userId, Long interactiveEntityId) {
        boolean alreadyLiked = likeRepository.existsByUserIdAndInteractiveEntityId(userId, interactiveEntityId);

        if (!alreadyLiked) {
            throw new ResourceNotFoundException("The like on this entity does not exist.");
        }

        likeRepository.deleteByUserIdAndInteractiveEntityId(userId, interactiveEntityId);
    }
}
