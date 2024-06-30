using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MongoDB.Bson;
using MongoDB.Driver;
using user_service_dotnet.config;
using user_service_dotnet.dtos;
using user_service_dotnet.Dtos;
using user_service_dotnet.Entities;
using user_service_dotnet.exception;

namespace user_service_dotnet.Services.impl
{
  public class UserImpl : IUserService
  {
    private readonly AppDbContext _context;

    public UserImpl(AppDbContext context)
    {
      _context = context;
    }

    public async Task<UserInfoDTO> GetUserById(string userId)
    {
      ObjectId objectId = new(userId);
      FilterDefinition<UserInfo> filter = Builders<UserInfo>.Filter.Eq("_id", objectId);
      UserInfo user = await _context.Users.Find(filter).FirstOrDefaultAsync() ?? throw new UserNotFoundException("User not found. Id: " + userId);

      UserInfoDTO userDto = new()
      {
        Email = user.Email,
        Username = user.Username,
        BannerPicture = user.BannerPicture,
        UserPicture = user.UserPicture
      };

      return userDto;
    }

    public async Task<UserListOrderDTO> GetUserListOrderById(string userId)
    {
      ObjectId objectId = new(userId);
      FilterDefinition<UserInfo> filter = Builders<UserInfo>.Filter.Eq("_id", objectId);
      UserInfo userListOrder = await _context.Users.Find(filter).FirstOrDefaultAsync() ?? throw new UserNotFoundException("User not found. Id: " + userId);

      UserListOrderDTO userListOrderDto = new()
      {
        ListsOrder = userListOrder.ListsOrder
      };

      return userListOrderDto;
    }

    public async Task<UserFollowDTO> GetAllFollowById(string userId)
    {
      var objectId = new ObjectId(userId);
      var user = await _context.Users.Find(u => u.Id == objectId).FirstOrDefaultAsync() ?? throw new KeyNotFoundException("User not found");
      // Retrieve all following users in one query
      var followingUsers = await _context.Users.Find(u => user.Following.Contains(u.Id)).ToListAsync();
      var followingDto = followingUsers.Select(followingUser => new UserBasicInfoDTO
      {
        Id = followingUser.Id.ToString(),
        Username = followingUser.Username,
        BannerPicture = followingUser.BannerPicture,
        UserPicture = followingUser.UserPicture
      }).ToList();

      var followerUsers = await _context.Users.Find(u => user.Followers.Contains(u.Id)).ToListAsync();
      var followersDto = followerUsers.Select(followerUser => new UserBasicInfoDTO
      {
        Id = followerUser.Id.ToString(),
        Username = followerUser.Username,
        BannerPicture = followerUser.BannerPicture,
        UserPicture = followerUser.UserPicture
      }).ToList();

      return new UserFollowDTO
      {
        Id = userId,
        Followers = followersDto,
        Following = followingDto
      };
    }

    public async Task<UserBasicInfoDTO> FollowUser(string followerUserId, string followedUserId)
    {
      var follower = new ObjectId(followerUserId);
      var following = new ObjectId(followedUserId);

      var followerUser = await _context.Users.Find(u => u.Id == follower).FirstOrDefaultAsync();
      var followedUser = await _context.Users.Find(u => u.Id == following).FirstOrDefaultAsync();
      if (followerUser == null || followedUser == null)
      {
        throw new KeyNotFoundException("One or both users not found");
      }

      if (!followerUser.Following.Contains(following))
      {
        var updateFollower = Builders<UserInfo>.Update.AddToSet(u => u.Following, following);
        await _context.Users.UpdateOneAsync(u => u.Id == follower, updateFollower);
      }

      if (!followedUser.Followers.Contains(follower))
      {
        var updateFollowed = Builders<UserInfo>.Update.AddToSet(u => u.Followers, follower);
        await _context.Users.UpdateOneAsync(u => u.Id == following, updateFollowed);
      }

      return new UserBasicInfoDTO
      {
        Id = followedUserId,
        Username = followedUser.Username,
        BannerPicture = followedUser.BannerPicture,
        UserPicture = followedUser.UserPicture
      };
    }

    public async Task<UserBasicInfoDTO> UnfollowUser(string followerUserId, string followedUserId)
    {
      var follower = new ObjectId(followerUserId);
      var following = new ObjectId(followedUserId);

      var followerUser = await _context.Users.Find(u => u.Id == follower).FirstOrDefaultAsync();
      var followedUser = await _context.Users.Find(u => u.Id == following).FirstOrDefaultAsync();
      if (followerUser == null || followedUser == null)
      {
        throw new KeyNotFoundException("One or both users not found");
      }

      if (followerUser.Following.Contains(following))
      {
        var updateFollower = Builders<UserInfo>.Update.Pull(u => u.Following, following);
        await _context.Users.UpdateOneAsync(u => u.Id == follower, updateFollower);
      }

      if (followedUser.Followers.Contains(follower))
      {
        var updateFollowed = Builders<UserInfo>.Update.Pull(u => u.Followers, follower);
        await _context.Users.UpdateOneAsync(u => u.Id == following, updateFollowed);
      }

      return new UserBasicInfoDTO
      {
        Id = followedUserId,
        Username = followedUser.Username,
        BannerPicture = followedUser.BannerPicture,
        UserPicture = followedUser.UserPicture
      };
    }

    public async Task<UserBasicInfoDTO> RemoveFollower(string followedUserId, string followerUserId)
    {
      var follower = new ObjectId(followerUserId);
      var followed = new ObjectId(followedUserId);

      var followerUser = await _context.Users.Find(u => u.Id == follower).FirstOrDefaultAsync();
      var followedUser = await _context.Users.Find(u => u.Id == followed).FirstOrDefaultAsync();
      if (followerUser == null || followedUser == null)
      {
        throw new UserNotFoundException("One or both users not found");
      }

      if (followerUser.Following.Contains(followed))
      {
        var updateFollower = Builders<UserInfo>.Update.Pull(u => u.Following, followed);
        await _context.Users.UpdateOneAsync(u => u.Id == follower, updateFollower);
      }

      if (followedUser.Followers.Contains(follower))
      {
        var updateFollowed = Builders<UserInfo>.Update.Pull(u => u.Followers, follower);
        await _context.Users.UpdateOneAsync(u => u.Id == followed, updateFollowed);
      }

      return new UserBasicInfoDTO
      {
        Id = followerUserId,
        Username = followerUser.Username,
        BannerPicture = followerUser.BannerPicture,
        UserPicture = followerUser.UserPicture
      };
    }
  }
}