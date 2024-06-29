
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace user_service_dotnet.Entities
{
  public class UserInfo
  {
    [BsonId]
    public ObjectId Id { get; set; }

    [BsonElement("email")]
    public required String Email { get; set; }

    [BsonElement("password")]
    public required string Password { get; set; }

    [BsonElement("banner_picture")]
    public string BannerPicture { get; set; } = string.Empty;

    [BsonElement("bio")]
    public string Bio { get; set; } = string.Empty;

    [BsonElement("is_activated")]
    public bool IsActivated { get; set; }

    [BsonElement("created_at")]
    public DateTime CreatedAt { get; set; }

    [BsonElement("listsorder")]
    public string ListsOrder { get; set; } = "planning,playing,completed,paused,dropped,justAdded";

    [BsonElement("updated_at")]
    public DateTime UpdatedAt { get; set; }

    [BsonElement("user_picture")]
    public string UserPicture { get; set; } = string.Empty;

    [BsonElement("username")]
    public required string Username { get; set; }

    [BsonElement("_class")]
    public string UserClass { get; set; } = "com.gamelist.seeding.entity.User";
  }
}