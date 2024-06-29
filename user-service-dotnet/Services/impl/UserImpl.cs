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
  }
}