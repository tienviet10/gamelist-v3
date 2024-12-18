using GameList.Game;
using Grpc.Core;


namespace user_service_dotnet.GrpcServices
{
  public class ExampleServiceImpl : ExampleService.ExampleServiceBase
  {
    public override Task<GameInformation> GetExampleInformation(GameInformationRequest request, ServerCallContext context)
    {
      return Task.FromResult(new GameInformation()
      {
        Name = "Game Name123...",
      });
    }
  }
}
