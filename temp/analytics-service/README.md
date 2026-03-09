# Analytics Service

**Owner:** Tarun
**Port:** 8086
**Service Name:** analytics-service

## Overview

The Analytics Service provides comprehensive analytics and insights for artists on the RevPlay platform. It aggregates play history data from the player-service and enriches it with information from artist-service and user-service to provide meaningful insights about song performance, listener engagement, and trends.

## Key Features

- **Artist Overview Analytics**: Total plays, unique listeners, listening time, and top songs
- **Song Performance Metrics**: Play counts, unique listeners, average listen duration, and completion rates
- **Listening Trends**: Daily/weekly/monthly trend analysis
- **Top Listeners**: Identify and track top fans
- **Period-based Analysis**: DAILY, WEEKLY, MONTHLY, ALL_TIME
- **Caching**: Aggressive caching for expensive analytics calculations
- **No Database**: Purely aggregation-based service

## Architecture

This service has NO database and operates entirely by:
1. Fetching raw play history data from player-service
2. Aggregating and calculating metrics in-memory
3. Enriching data with artist and user information
4. Caching results for performance

## Technology Stack

- Spring Boot 3.2.0
- Spring Cloud (Eureka Client, OpenFeign)
- Resilience4j (Circuit Breakers)
- Caffeine Cache
- Java 17

## API Endpoints

### Artist Analytics

#### Get Artist Overview
```
GET /api/analytics/artist/{artistId}/overview?period=WEEKLY
```

**Query Parameters:**
- `period` (optional): DAILY, WEEKLY, MONTHLY, ALL_TIME (default: ALL_TIME)

**Response:**
```json
{
  "totalPlays": 15430,
  "uniqueListeners": 2341,
  "totalListeningTimeMinutes": 45678.5,
  "averagePlayDuration": 178.2,
  "topSongId": 123,
  "topSongTitle": "Summer Nights",
  "period": "WEEKLY"
}
```

#### Get Song Performance
```
GET /api/analytics/artist/{artistId}/songs
```

**Response:**
```json
[
  {
    "songId": 123,
    "title": "Summer Nights",
    "playCount": 5432,
    "uniqueListeners": 1234,
    "averageListenDuration": 192.5,
    "completionRate": 87.3
  }
]
```

#### Get Top Songs
```
GET /api/analytics/artist/{artistId}/top?limit=5&period=MONTHLY
```

**Query Parameters:**
- `limit` (optional): 1-100 (default: 10)
- `period` (optional): DAILY, WEEKLY, MONTHLY, ALL_TIME (default: ALL_TIME)

**Response:**
```json
{
  "songs": [...],
  "period": "MONTHLY",
  "artistId": 456
}
```

#### Get Listening Trends
```
GET /api/analytics/artist/{artistId}/trends?startDate=2024-01-01&endDate=2024-01-31
```

**Query Parameters:**
- `startDate` (optional): ISO date (default: 30 days ago)
- `endDate` (optional): ISO date (default: today)

**Response:**
```json
{
  "trends": [
    {
      "date": "2024-01-01",
      "playCount": 234,
      "uniqueListeners": 156,
      "totalMinutes": 678.5
    }
  ]
}
```

#### Get Top Listeners
```
GET /api/analytics/artist/{artistId}/listeners?limit=20
```

**Query Parameters:**
- `limit` (optional): 1-100 (default: 10)

**Response:**
```json
[
  {
    "userId": 789,
    "username": "music_lover_123",
    "playCount": 342,
    "totalListeningMinutes": 1234.5
  }
]
```

## Security

- **Authentication**: Requires `X-User-Id` and `X-User-Role` headers
- **Authorization**: Only artists (role=ARTIST) can access analytics
- **Ownership**: Artists can only view analytics for their own content (artistId must match userId)

## Service Dependencies

### Player Service
- `GET /api/internal/history/artist/{artistId}/listeners` - Get listener stats
- `GET /api/internal/history/song/{songId}` - Get song play history
- `GET /api/internal/history/trends` - Get play trends with date range

### Artist Service
- `GET /api/artists/{artistId}` - Get artist information
- `GET /api/artists/{artistId}/songs` - Get artist's songs
- `GET /api/internal/songs/{songId}` - Get song details

### User Service
- `GET /api/users/{userId}` - Get user information (for enriching listener data)

## Caching Strategy

The service uses Caffeine cache with the following configuration:
- **Max Size**: 1000 entries
- **TTL**: 5 minutes
- **Cached Operations**:
  - Artist overview
  - Song performance
  - Top songs
  - Listening trends
  - Top listeners
  - Daily stats aggregation
  - Completion rates
  - Unique listener counts

## Circuit Breakers

All external service calls are protected with Resilience4j circuit breakers:
- **Sliding Window Size**: 10 calls
- **Failure Rate Threshold**: 50%
- **Wait Duration**: 30 seconds
- **Half-Open State**: 3 permitted calls

## Configuration

### Application Properties
```yaml
server:
  port: 8086

spring:
  application:
    name: analytics-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Running the Service

### Using Maven
```bash
cd analytics-service
mvn clean install
mvn spring-boot:run
```

### Using Docker
```bash
docker build -t analytics-service .
docker run -p 8086:8086 analytics-service
```

## Development Notes

- **No Lombok**: Uses manual builder pattern for all DTOs
- **No Database**: All data is aggregated from other services
- **Performance**: Heavy use of caching due to expensive aggregations
- **Resilience**: Circuit breakers protect against downstream service failures
- **Scalability**: Stateless design allows horizontal scaling

## Error Handling

- `403 Forbidden`: Unauthorized access (non-artist or wrong artist)
- `400 Bad Request`: Invalid parameters (e.g., invalid date range)
- `503 Service Unavailable`: Downstream service failure
- `500 Internal Server Error`: Unexpected errors

## Testing Considerations

When testing this service:
1. Mock Feign clients for unit tests
2. Use WireMock for integration tests
3. Test circuit breaker behavior
4. Verify cache effectiveness
5. Test authorization logic

## Future Enhancements

- Real-time analytics using streaming data
- More granular time-based analysis (hourly trends)
- Geographic analytics (where are listeners from)
- Demographic insights
- Revenue analytics integration
- Export functionality (CSV, PDF reports)
- Scheduled reports via email
