#See https://aka.ms/customizecontainer to learn how to customize your debug container and how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["gateways/websocket-gateway/websocket-gateway.csproj", "gateways/websocket-gateway/"]
RUN dotnet restore "gateways/websocket-gateway/websocket-gateway.csproj"
COPY . .
WORKDIR "/src/gateways/websocket-gateway"
RUN dotnet build "websocket-gateway.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "websocket-gateway.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "websocket-gateway.dll"]