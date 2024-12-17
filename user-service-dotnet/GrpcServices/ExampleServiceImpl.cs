using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using GameList.Game;
using Grpc.Core;


namespace user_service_dotnet.GrpcServices
{
  public class ExampleServiceImpl : ExampleService.ExampleServiceBase
  {
    // public override Task<GreetingResponse> Greet(GameInformationRequest request, ServerCallContext context)
    // {
    //   string result = String.Format("hello {0} {1}", request.Greeting.FirstName, request.Greeting.LastName);

    //   return Task.FromResult(new GreetingResponse() { Result = result });
    // }
    public override Task<GameInformation> GetExampleInformation(GameInformationRequest request, ServerCallContext context)
    {
      return Task.FromResult(new GameInformation()
      {
        Name = "Game Name123...",
      });
    }
  }
}
