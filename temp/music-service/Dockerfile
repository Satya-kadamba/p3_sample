# Multi-stage build for Music Service
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/music-service-1.0.0.jar app.jar

# Expose port
EXPOSE 8084

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://discovery-server:8761/eureka/

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
