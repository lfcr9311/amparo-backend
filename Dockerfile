from gradle:latest AS builder

WORKDIR /app

copy . .

run gradle clean build --no-daemon -x test

from amazoncorretto:17-alpine3.18-jdk

WORKDIR /app

copy --from=builder /amparo-backend/build/libs/amparo-backend-0.0.1-SNAPSHOT.jar app.jar

CMD java -jar app.jar