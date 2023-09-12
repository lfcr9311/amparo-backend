# Build stage
FROM gradle:latest AS builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon -x test

# Package stage
FROM amazoncorretto:17-alpine3.18-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/amparo-backend-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar