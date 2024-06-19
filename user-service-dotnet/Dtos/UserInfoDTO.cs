using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace user_service_dotnet.dtos
{
  public class UserInfoDTO
  {
    public string Email { get; set; }
    public string Username { get; set; }
    public string BannerPicture { get; set; }
    public string UserPicture { get; set; }
  }
}