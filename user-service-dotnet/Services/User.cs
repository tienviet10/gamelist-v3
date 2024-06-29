using user_service_dotnet.dtos;
using user_service_dotnet.Dtos;

namespace user_service_dotnet.Services
{
  public interface IUserService
  {
    Task<UserInfoDTO> GetUserById(string userId);
    Task<UserListOrderDTO> GetUserListOrderById(string userId);
  }
}