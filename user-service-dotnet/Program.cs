using Prometheus;
using OpenTelemetry;
using OpenTelemetry.Resources;
using OpenTelemetry.Trace;
using user_service_dotnet.config;
using user_service_dotnet.Services;
using user_service_dotnet.Services.impl;
using user_service_dotnet.Prometheus;
using Grpc.Core;
using GameList.Game;
using Grpc.Reflection;
using Grpc.Reflection.V1Alpha;
using user_service_dotnet.GrpcServices;

var builder = WebApplication.CreateBuilder(args);
var zipkinEndpoint = builder.Configuration.GetValue<string>("Zipkin:Endpoint");
var gRPCPort = builder.Configuration.GetValue<string>("Grpc:ServerAddress");

if (string.IsNullOrEmpty(zipkinEndpoint))
{
  Console.WriteLine("Zipkin endpoint is not configured");
}

if (string.IsNullOrEmpty(gRPCPort))
{
  Console.WriteLine("gRPC Port is not configured");
}

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.UseHttpClientMetrics();
builder.Services.AddSingleton<AppDbContext>();
builder.Services.AddScoped<IUserService, UserImpl>();

Server server;
var reflectionServiceImpl = new ReflectionServiceImpl(ExampleService.Descriptor, UserService.Descriptor, ServerReflection.Descriptor);
var userServiceImpl = builder.Services.BuildServiceProvider().GetRequiredService<IUserService>();
server = new Server()
{
  Services = {
    ExampleService.BindService(new ExampleServiceImpl()),
    UserService.BindService(new UserServiceImpl(userServiceImpl)),
    ServerReflection.BindService(reflectionServiceImpl),
  },
  Ports = { new ServerPort("0.0.0.0", int.Parse(gRPCPort), ServerCredentials.Insecure) }
};

server.Start();
Console.WriteLine("The gRPC server is listening on the port : " + gRPCPort);

builder.Services.AddOpenTelemetry().WithTracing(builder => builder
  .SetResourceBuilder(ResourceBuilder.CreateDefault().AddService("user-service"))
  .AddAspNetCoreInstrumentation()
  .AddZipkinExporter(o =>
  {
    o.Endpoint = new Uri(zipkinEndpoint);
    o.HttpClientFactory = () =>
    {
      var client = new HttpClient();
      client.DefaultRequestHeaders.Add("X-MyCustomHeader", "value");
      return client;
    };
  })
);

builder.Services.AddCors(options =>
{
  options.AddPolicy("AllowAll", builder => builder.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader());
});

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
  app.UseSwagger();
  app.UseSwaggerUI();
}

app.Use(async (context, next) =>
{
  var path = context.Request.Path.HasValue ? context.Request.Path.Value : "";
  await next();

  var status = context.Response.StatusCode.ToString();
  var method = context.Request.Method;

  MetricsRegistry.HttpRequestTotal.WithLabels("user-service", method, status, path).Inc();
});

app.UseMetricServer();
app.UseHttpMetrics();

// app.UseHttpsRedirection();
app.MapControllers();
app.UseCors("AllowAll");

app.Run();