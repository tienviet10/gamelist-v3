package com.gamelist.social_service.service.impl;

import com.gamelist.social_service.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    //    private static final String USER_NOT_FOUND_MSG = "User not found";
    ////    private final UserRepository userRepository;
    //
    //    @Override
    //    public UserBasicView createFollow(Long tokenUserId, Long userId) {
    //        if (tokenUserId.equals(userId)) {
    //            throw new InvalidInputException("You cannot follow yourself.");
    //        }
    //
    //        boolean alreadyFollowed = userRepository.existsInFollowingByIdAndFollowersId(userId, tokenUserId);
    //
    //        if (alreadyFollowed) {
    //            throw new InvalidInputException("You have already followed this user.");
    //        }
    //
    //        User user = userRepository
    //                .findWithFollowersAndFollowingById(tokenUserId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //
    //        User userToFollow = userRepository
    //                .findWithFollowersAndFollowingById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //
    //        user.addFollowing(userToFollow);
    //
    //        userRepository.save(user);
    //
    //        return userRepository
    //                .findBasicViewById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //    }
    //
    //    @Override
    //    public UserBasicView removeFollow(Long tokenUserId, Long userId) {
    //        if (tokenUserId.equals(userId)) {
    //            throw new InvalidInputException("You cannot follow or remove yourself.");
    //        }
    //
    //        boolean alreadyFollowed = userRepository.existsInFollowingByIdAndFollowersId(userId, tokenUserId);
    //        if (!alreadyFollowed) {
    //            throw new InvalidInputException("Can not found this user in your following.");
    //        }
    //
    //        User user = userRepository
    //                .findWithFollowersAndFollowingById(tokenUserId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //        User userToUnfollow = userRepository
    //                .findWithFollowersAndFollowingById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //
    //        user.removeFollowing(userToUnfollow);
    //        userRepository.save(user);
    //
    //        return userRepository
    //                .findBasicViewById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //    }
    //
    //    @Override
    //    public UserBasicView removeFollower(Long tokenUserId, Long userId) {
    //        if (tokenUserId.equals(userId)) {
    //            throw new InvalidInputException("You cannot follow or remove yourself.");
    //        }
    //
    //        boolean isFollowerExist = userRepository.existsInFollowersByIdAndFollowersId(tokenUserId, userId);
    //
    //        if (!isFollowerExist) {
    //            throw new InvalidInputException("Can not found this user in your followers.");
    //        }
    //
    //        User user = userRepository
    //                .findWithFollowersAndFollowingById(tokenUserId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //        User userToRemoveInFollowers = userRepository
    //                .findWithFollowersAndFollowingById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //
    //        user.removeFollower(userToRemoveInFollowers);
    //        userRepository.save(user);
    //
    //        return userRepository
    //                .findBasicViewById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //    }
    //
    //    @Override
    //    public FollowView getAllFollows(Long userId) {
    //        return userRepository
    //                .findFollowViewViewWithFollowersAndFollowingById(userId)
    //                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MSG));
    //    }
}
