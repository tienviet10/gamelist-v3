using MongoDB.Driver;
using user_service_dotnet.Entities;


namespace user_service_dotnet.config
{
  public class AppDbContext
  {
    private readonly IMongoDatabase _database;

    public AppDbContext(IConfiguration configuration)
    {
      var dbConnectionUrl = Environment.GetEnvironmentVariable("VAR_MONGO_DB");
      if (string.IsNullOrEmpty(dbConnectionUrl))
      {
        dbConnectionUrl = configuration["ConnectionStrings:MongoDb"];
        Console.WriteLine($"Connection string: {dbConnectionUrl}");
      }
      var databaseName = configuration.GetConnectionString("DatabaseName");

      var client = new MongoClient(dbConnectionUrl);
      _database = client.GetDatabase(databaseName);
    }

    public IMongoCollection<UserInfo> Users =>
        _database.GetCollection<UserInfo>("users");
  }
}