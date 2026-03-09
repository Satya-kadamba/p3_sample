# RevPlay Microservices

A music streaming platform built with Spring Boot microservices architecture.

## Architecture

```
                    ┌─────────────────┐
                    │   API Gateway   │
                    │    (8080)       │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│Auth Service │     │User Service │     │Artist Service│
│   (8081)    │     │   (8082)    │     │   (8083)    │
│   [Satya]   │     │  [Satish]   │     │ [Srinivas]  │
└─────────────┘     └─────────────┘     └─────────────┘
                           │                   │
                           │      ┌────────────┘
                           ▼      ▼
                    ┌─────────────┐
                    │Music Service│
                    │   (8084)    │
                    │[Prithivirajan]│
                    └─────────────┘
                           │
         ┌─────────────────┼─────────────────┐
         ▼                 │                 ▼
┌─────────────┐            │        ┌─────────────┐
│Player Service│           │        │Analytics Svc│
│   (8085)    │────────────┘        │   (8086)    │
│  [Deeja]    │                     │   [Tarun]   │
└─────────────┘                     └─────────────┘
```

## Services

| Service | Port | Description | Owner |
|---------|------|-------------|-------|
| Config Server | 8888 | Centralized configuration | Shared |
| Discovery Server | 8761 | Eureka service registry | Shared |
| API Gateway | 8080 | Request routing & JWT validation | Shared |
| Auth Service | 8081 | Authentication & JWT tokens | Satya |
| User Service | 8082 | User profiles, playlists, favorites | Satish |
| Artist Service | 8083 | Artist profiles, songs, albums | Srinivas |
| Music Service | 8084 | Music discovery & search | Prithivirajan |
| Player Service | 8085 | Play music & listening history | Deeja |
| Analytics Service | 8086 | Artist analytics & insights | Tarun |

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (optional)

## Running Locally

### 1. Build all services
```bash
mvn clean package -DskipTests
```

### 2. Start services in order
```bash
# Terminal 1: Config Server
cd config-server && mvn spring-boot:run

# Terminal 2: Discovery Server (wait for config server)
cd discovery-server && mvn spring-boot:run

# Terminal 3: API Gateway
cd api-gateway && mvn spring-boot:run

# Terminal 4-9: Business services (can start in parallel)
cd auth-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd artist-service && mvn spring-boot:run
cd music-service && mvn spring-boot:run
cd player-service && mvn spring-boot:run
cd analytics-service && mvn spring-boot:run
```

### Using Docker Compose
```bash
mvn clean package -DskipTests
docker-compose up --build
```

## API Endpoints

All requests go through the API Gateway at `http://localhost:8080`

### Auth Service (Satya)
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT
- `POST /api/auth/refresh` - Refresh JWT token
- `POST /api/auth/logout` - Logout
- `GET /api/auth/validate` - Validate JWT token

### User Service (Satish)
- `GET /api/users/profile` - Get current user profile
- `PUT /api/users/profile` - Update profile
- `GET /api/users/stats` - Get user statistics
- `POST /api/playlists` - Create playlist
- `GET /api/playlists` - Get user's playlists
- `PUT /api/playlists/{id}` - Update playlist
- `DELETE /api/playlists/{id}` - Delete playlist
- `POST /api/playlists/{id}/songs/{songId}` - Add song to playlist
- `DELETE /api/playlists/{id}/songs/{songId}` - Remove song
- `POST /api/favorites/{songId}` - Add to favorites
- `DELETE /api/favorites/{songId}` - Remove from favorites
- `GET /api/favorites` - Get favorites

### Artist Service (Srinivas)
- `GET /api/artists/{artistId}` - Get artist profile
- `PUT /api/artists/{artistId}` - Update artist profile
- `POST /api/artists/{artistId}/songs` - Upload song
- `GET /api/artists/{artistId}/songs` - Get artist's songs
- `PUT /api/artists/{artistId}/songs/{songId}` - Update song
- `DELETE /api/artists/{artistId}/songs/{songId}` - Delete song
- `POST /api/artists/{artistId}/albums` - Create album
- `GET /api/artists/{artistId}/albums` - Get albums
- `PUT /api/artists/{artistId}/albums/{albumId}` - Update album
- `DELETE /api/artists/{artistId}/albums/{albumId}` - Delete album

### Music Service (Prithivirajan)
- `GET /api/catalog/songs` - Browse public songs
- `GET /api/catalog/songs/{songId}` - Get song details
- `GET /api/catalog/songs/search` - Search songs
- `GET /api/catalog/genres` - Get available genres
- `GET /api/catalog/trending` - Get trending songs

### Player Service (Deeja)
- `POST /api/player/play` - Record play event
- `GET /api/player/now-playing` - Get current playing
- `GET /api/history/recent` - Get recent history
- `DELETE /api/history` - Clear history

### Analytics Service (Tarun)
- `GET /api/analytics/artist/{artistId}/overview` - Overview stats
- `GET /api/analytics/artist/{artistId}/songs` - Song performance
- `GET /api/analytics/artist/{artistId}/top` - Top songs
- `GET /api/analytics/artist/{artistId}/trends` - Listening trends
- `GET /api/analytics/artist/{artistId}/listeners` - Top listeners

## Technology Stack

- Spring Boot 3.2
- Spring Cloud (Config, Eureka, Gateway)
- Spring Security + JWT
- Spring Data JPA
- H2 Database (dev)
- OpenFeign
- Resilience4j
- Docker
