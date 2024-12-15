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

var builder = WebApplication.CreateBuilder(args);
var zipkinEndpoint = builder.Configuration.GetValue<string>("Zipkin:Endpoint");

if (string.IsNullOrEmpty(zipkinEndpoint))
{
  Console.WriteLine("Zipkin endpoint is not configured");
}
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.UseHttpClientMetrics();
builder.Services.AddSingleton<AppDbContext>();
builder.Services.AddScoped<IUserService, UserImpl>();

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