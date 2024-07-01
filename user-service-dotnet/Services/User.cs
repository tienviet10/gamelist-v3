using user_service_dotnet.dtos;
using user_service_dotnet.Dtos;

namespace user_service_dotnet.Services
{
  public interface IUserService
  {
    Task<UserInfoDTO> GetUserById(string userId);
    Task<UserListOrderDTO> GetUserListOrderById(string userId);
    Task<UserFollowDTO> GetAllFollowById(string userId);
    Task<UserBasicInfoDTO> FollowUser(string followerUserId, string followedUserId);
    Task<UserBasicInfoDTO> UnfollowUser(string followerUserId, string followedUserId);
    Task<UserBasicInfoDTO> RemoveFollower(string followedUserId, string followerUserId);
    Task<bool> UserExist(string checkingUserExist);
  }
}