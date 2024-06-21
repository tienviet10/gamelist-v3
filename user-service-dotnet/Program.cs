using Prometheus;
using user_service_dotnet.config;
using user_service_dotnet.Services;
using user_service_dotnet.Services.impl;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.UseHttpClientMetrics();
builder.Services.AddSingleton<AppDbContext>();
builder.Services.AddScoped<IUserService, UserImpl>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
  app.UseSwagger();
  app.UseSwaggerUI();
}

app.UseMetricServer();
app.UseHttpMetrics();

// app.UseHttpsRedirection();
app.MapControllers();

app.Run();