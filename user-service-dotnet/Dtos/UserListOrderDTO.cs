using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace user_service_dotnet.Dtos
{
  public class UserListOrderDTO
  {
    public string ListsOrder { get; set; } = "planning,playing,completed,paused,dropped,justAdded";
  }
}