using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MongoDB.Bson;

namespace user_service_dotnet.Dtos
{
  public class UserBasicInfoDTO
  {
    public String Id { get; set; }
    public string Username { get; set; }
    public string BannerPicture { get; set; }
    public string UserPicture { get; set; }
  }
}