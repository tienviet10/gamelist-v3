using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace user_service_dotnet.Controllers
{
  public class CustomHttpResponse<T>
  {

    public DateTime TimeStamp { get; set; } = DateTime.UtcNow;

    public int StatusCode { get; set; }

    public String Status { get; set; } = HttpStatusCode.OK.ToString();

    public string Message { get; set; }

    public string DeveloperMessage { get; set; }

    public string Path { get; set; }

    public string RequestMethod { get; set; }

    public T Data { get; set; }
  }
}