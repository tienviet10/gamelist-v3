using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace user_service_dotnet.Dtos
{
  public class UserFollowDTO
  {
    public required string Id { get; set; }
    public List<UserBasicInfoDTO>? Following { get; set; }
    public List<UserBasicInfoDTO>? Followers { get; set; }
  }
}