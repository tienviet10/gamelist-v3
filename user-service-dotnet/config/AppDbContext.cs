using MongoDB.Driver;
using user_service_dotnet.Entities;


namespace user_service_dotnet.config
{
  public class AppDbContext
  {
    private readonly IMongoDatabase _database;

    public AppDbContext(IConfiguration configuration)
    {
      var connectionString = configuration.GetConnectionString("MongoDB");
      var databaseName = configuration.GetConnectionString("DatabaseName");

      var client = new MongoClient(connectionString);
      _database = client.GetDatabase(databaseName);
    }

    public IMongoCollection<UserInfo> Users =>
        _database.GetCollection<UserInfo>("users");
  }
}