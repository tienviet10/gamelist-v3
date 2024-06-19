using user_service_dotnet.dtos;

namespace user_service_dotnet.Services
{
  public interface IUserService
  {
    Task<UserInfoDTO> GetUserById(string userId);
  }
}