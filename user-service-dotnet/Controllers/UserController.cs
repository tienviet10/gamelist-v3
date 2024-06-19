using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;
using user_service_dotnet.config;
using user_service_dotnet.Entities;

namespace user_service_dotnet.Controllers
{
  public class UserController(AppDbContext context) : ControllerBase
  {
    [HttpGet("users")]
    public async Task<IActionResult> GetList()
    {
      var myEntities = await context.Users.Find(Builders<UserInfo>.Filter.Empty).ToListAsync();
      return Ok(myEntities);
    }
  }
}