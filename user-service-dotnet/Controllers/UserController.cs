using System.Net;
using Microsoft.AspNetCore.Mvc;
using user_service_dotnet.dtos;
using user_service_dotnet.Entities;
using user_service_dotnet.exception;
using user_service_dotnet.Services;

namespace user_service_dotnet.Controllers
{
  [Route("api/v1/user")]
  public class UserController : ControllerBase
  {
    private readonly IUserService _userService;

    public UserController(IUserService userService)
    {
      _userService = userService;
    }

    [HttpGet("userinfo")]
    public async Task<ActionResult<CustomHttpResponse>> GetUser()
    {
      string userId = Request.Headers["userId"].ToString();

      try
      {
        UserInfoDTO userDto = await _userService.GetUserById("666a602d5d13cfef6ed583ac");

        return Ok(new CustomHttpResponse
        {
          StatusCode = 200,
          Status = HttpStatusCode.OK,
          Message = "User found",
          DeveloperMessage = "User found successfully",
          Path = "/api/v1/user/userinfo",
          RequestMethod = "GET",
          Data = new Dictionary<string, object> { { "user", userDto } }
        });
      }
      catch (UserNotFoundException ex)
      {
        return NotFound(new ErrorDetails(DateTime.UtcNow, ex.Message, "No user found with the provided userId"));
      }
    }
  }
}