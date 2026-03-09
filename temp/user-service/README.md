# User Service - RevPlay

**Owner:** Satish
**Port:** 8082

## Overview
The User Service manages user profiles, playlists, and favorite songs for the RevPlay music streaming platform.

## Features
- User Profile Management
- Playlist Creation and Management
- Favorite Songs Management
- Song Organization and Ordering

## Technology Stack
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Cloud Netflix Eureka Client
- Spring Cloud OpenFeign
- Resilience4j (Circuit Breaker)
- H2 Database (In-memory)
- Jakarta Validation

## API Endpoints

### User Profile Endpoints
- `GET /api/users/profile` - Get current user profile
- `POST /api/users/profile` - Create user profile
- `PUT /api/users/profile` - Update user profile
- `GET /api/users/stats` - Get user statistics
- `GET /api/users/{userId}` - Get user by ID

### Playlist Endpoints
- `POST /api/playlists` - Create a new playlist
- `GET /api/playlists` - Get all user playlists
- `GET /api/playlists/{id}` - Get playlist by ID with songs
- `PUT /api/playlists/{id}` - Update playlist
- `DELETE /api/playlists/{id}` - Delete playlist
- `POST /api/playlists/{id}/songs` - Add song to playlist
- `DELETE /api/playlists/{id}/songs/{songId}` - Remove song from playlist

### Favorite Endpoints
- `POST /api/favorites/{songId}` - Add song to favorites
- `DELETE /api/favorites/{songId}` - Remove song from favorites
- `GET /api/favorites` - Get all favorite songs
- `GET /api/favorites/{songId}/check` - Check if song is favorited

## Configuration
The service requires the following header for authentication:
- `X-User-Id`: User ID (set by API Gateway)

## Building and Running

### Local Development
```bash
mvn clean install
mvn spring-boot:run
```

### Docker
```bash
docker build -t user-service .
docker run -p 8082:8082 user-service
```

## Service Dependencies
- **Eureka Server** (localhost:8761) - Service Discovery
- **Artist Service** - For fetching song details via Feign Client

## Database
Uses H2 in-memory database. Access H2 console at:
- URL: http://localhost:8082/h2-console
- JDBC URL: jdbc:h2:mem:userdb
- Username: sa
- Password: (empty)

## Circuit Breaker
Resilience4j circuit breaker is configured for the Artist Service client with:
- Failure Rate Threshold: 50%
- Wait Duration in Open State: 5s
- Sliding Window Size: 10
- Timeout Duration: 4s

## Health Check
- http://localhost:8082/actuator/health

## Notes
- User ID is extracted from the `X-User-Id` header (set by API Gateway)
- No Lombok - uses manual builder pattern
- Includes comprehensive validation using Jakarta Validation
