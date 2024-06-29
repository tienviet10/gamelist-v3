using System.Net;
using Microsoft.AspNetCore.Mvc;
using user_service_dotnet.dtos;
using user_service_dotnet.Entities;
using user_service_dotnet.exception;
using user_service_dotnet.Services;
using System.Diagnostics;
using user_service_dotnet.Dtos;
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

    [HttpGet("userinfo")]
    public async Task<ActionResult<CustomHttpResponse<object>>> GetUser()
    {
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
      Console.WriteLine("Getting user list order");
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
  }
}