using System.Net;
using Microsoft.AspNetCore.Mvc;
using user_service_dotnet.dtos;
using user_service_dotnet.Entities;
using user_service_dotnet.exception;
using user_service_dotnet.Services;
using System.Diagnostics;
using user_service_dotnet.Dtos;
// using Grpc.Core;
// using GameList.Game;
namespace user_service_dotnet.Controllers
{
  [Route("api/v1/user")]
  public class UserController : ControllerBase
  {
    private readonly IUserService _userService;
    private readonly ILogger<UserController> _logger;

    public UserController(IUserService userService, ILogger<UserController> logger)
    {
      _userService = userService;
      _logger = logger;
    }

    [HttpGet("userinfo/{userInfoId}")]
    public async Task<ActionResult<CustomHttpResponse<object>>> GetUserInfo(string userInfoId)
    {
      string userId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Getting user info for ID: {userId}, Trace ID: {traceId}");

      try
      {
        UserBasicInfoWithoutIdDTO userDto = await _userService.GetUserBasicInfoById(userInfoId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User found",
          DeveloperMessage = "User found successfully",
          Path = "/api/v1/user/userinfo",
          RequestMethod = "GET",
          Data = userDto
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpGet("userinfo")]
    public async Task<ActionResult<CustomHttpResponse<object>>> GetUser()
    {
      // Channel channel = new("localhost", 6565, ChannelCredentials.Insecure);
      // await channel.ConnectAsync().ContinueWith((task) =>
      //  {
      //    if (task.Status == TaskStatus.RanToCompletion)
      //      Console.WriteLine("The client connected successfully");
      //  });

      // var client = new ExampleService.ExampleServiceClient(channel);
      // var example = new GameInformationRequest() { GameId = 2222 };
      // channel.ShutdownAsync().Wait();

      string userId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Getting user info for ID: {userId}, Trace ID: {traceId}");

      try
      {
        UserInfoDTO userDto = await _userService.GetUserById(userId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User found",
          DeveloperMessage = "User found successfully",
          Path = "/api/v1/user/userinfo",
          RequestMethod = "GET",
          Data = userDto
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpGet("listorder")]
    public async Task<ActionResult<CustomHttpResponse<object>>> GetUserListOrder()
    {
      string userId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Getting user list order for ID: {userId}, Trace ID: {traceId}");

      try
      {
        UserListOrderDTO userListOrderDto = await _userService.GetUserListOrderById(userId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User list order found",
          DeveloperMessage = "User list order found successfully",
          Path = "/api/v1/user/listorder",
          RequestMethod = "GET",
          Data = userListOrderDto
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpGet("all-follow")]
    public async Task<ActionResult<CustomHttpResponse<object>>> GetAllFollow()
    {
      string userId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Getting all follow for ID: {userId}, Trace ID: {traceId}");

      try
      {
        UserFollowDTO userFollowDto = await _userService.GetAllFollowById(userId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User follow found",
          DeveloperMessage = "User follow found successfully",
          Path = "/api/v1/user/all-follow",
          RequestMethod = "PUT",
          Data = new { user = userFollowDto }
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpPost("follows/{followedUserId}")]
    public async Task<ActionResult<CustomHttpResponse<object>>> FollowUser(string followedUserId)
    {
      string followerUserId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Following user with ID: {followedUserId}, Trace ID: {traceId}");

      try
      {
        UserBasicInfoDTO userDto = await _userService.FollowUser(followerUserId, followedUserId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User followed",
          DeveloperMessage = "User followed successfully",
          Path = $"/api/v1/user/follows/{followedUserId}",
          RequestMethod = "PUT",
          Data = userDto
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpDelete("unfollow/{followedUserId}")]
    public async Task<ActionResult<CustomHttpResponse<object>>> UnfollowUser(string followedUserId)
    {
      string followerUserId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Unfollowing user with ID: {followedUserId}, Trace ID: {traceId}");

      try
      {
        UserBasicInfoDTO userDto = await _userService.UnfollowUser(followerUserId, followedUserId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User unfollowed",
          DeveloperMessage = "User unfollowed successfully",
          Path = $"/api/v1/user/unfollow/{followedUserId}",
          RequestMethod = "DELETE",
          Data = userDto
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpDelete("remove-follower/{followerUserId}")]
    public async Task<ActionResult<CustomHttpResponse<object>>> RemoveFollower(string followerUserId)
    {
      string followedUserId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Removing follower with ID: {followerUserId}, Trace ID: {traceId}");

      try
      {
        UserBasicInfoDTO userDto = await _userService.RemoveFollower(followedUserId, followerUserId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "Follower removed",
          DeveloperMessage = "Follower removed successfully",
          Path = $"/api/v1/user/remove-follower/{followerUserId}",
          RequestMethod = "DELETE",
          Data = userDto
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }

    [HttpGet("user-exist")]
    public async Task<ActionResult<CustomHttpResponse<object>>> UserExist()
    {
      string checkingUserId = Request.Headers["userId"].ToString();
      var traceId = Activity.Current?.TraceId.ToString() ?? "Unavailable";

      _logger.LogInformation($"Check if a user with ID exists: {checkingUserId}, Trace ID: {traceId}");

      try
      {
        bool userExist = await _userService.UserExist(checkingUserId);

        return Ok(new CustomHttpResponse<object>
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK.ToString(),
          Message = "User exists",
          DeveloperMessage = "Check the user successfully",
          Path = $"/api/v1/user/user-exist",
          RequestMethod = "GET",
          Data = userExist
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }
  }
}