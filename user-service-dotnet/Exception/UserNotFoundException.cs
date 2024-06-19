using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using user_service_dotnet.Entities;

namespace user_service_dotnet.exception
{
  public class UserNotFoundException : Exception
  {
    public ErrorDetails ErrorDetails { get; }

    public UserNotFoundException(string message) : base(message)
    {
      ErrorDetails = new ErrorDetails(DateTime.UtcNow, message, "No user found with the provided userId");
    }
  }
}