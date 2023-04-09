#See https://aka.ms/customizecontainer to learn how to customize your debug container and how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["servers/admin-server/admin-server.csproj", "servers/admin-server/"]
RUN dotnet restore "servers/admin-server/admin-server.csproj"
COPY . .
WORKDIR "/src/servers/admin-server"
RUN dotnet build "admin-server.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "admin-server.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "admin-server.dll"]