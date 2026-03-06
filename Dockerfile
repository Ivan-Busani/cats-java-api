# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Gradle wrapper and project config
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Source
COPY src src

# Use Java 21 in container (project may request 25 locally)
RUN sed -i 's/JavaLanguageVersion.of(25)/JavaLanguageVersion.of(21)/' build.gradle

# Build (no daemon, reproducible)
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
