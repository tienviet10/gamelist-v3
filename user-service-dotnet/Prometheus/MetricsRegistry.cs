using Prometheus;

namespace user_service_dotnet.Prometheus
{
  public static class MetricsRegistry
  {
    public static readonly Counter HttpRequestTotal = Metrics.CreateCounter(
        "http_server_requests_seconds_count",
        "Total number of HTTP requests",
        new CounterConfiguration
        {
          LabelNames = new[] { "application", "method", "status", "uri" }
        });
  }
}