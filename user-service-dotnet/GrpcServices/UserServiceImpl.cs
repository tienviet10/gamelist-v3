using GameList.Game;
using Grpc.Core;
using user_service_dotnet.Dtos;
using user_service_dotnet.Services;


namespace user_service_dotnet.GrpcServices
{
  public class UserServiceImpl(IUserService userService) : UserService.UserServiceBase
  {
    private readonly IUserService _userService = userService;

    public override async Task<UserCategoryList> GetUserCategoryListsInfoById(UserIdRequest request, ServerCallContext context)
    {
      UserListOrderDTO userListOrderDto = await _userService.GetUserListOrderById(request.UserId);
      return new UserCategoryList()
      {
        CategoryList = userListOrderDto.ListsOrder,
      };
    }
  }
}
