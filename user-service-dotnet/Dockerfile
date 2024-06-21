FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
USER app
WORKDIR /app
EXPOSE 8080

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

ARG VAR_MONGO_DB

COPY ["user-service-dotnet.csproj", "./"]
RUN dotnet restore "user-service-dotnet.csproj"

COPY . .
RUN dotnet build "user-service-dotnet.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "user-service-dotnet.csproj" -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS final
WORKDIR /app
COPY --from=publish /app/publish .

ARG VAR_MONGO_DB
ENV VAR_MONGO_DB=$VAR_MONGO_DB

ENTRYPOINT ["dotnet", "user-service-dotnet.dll"]