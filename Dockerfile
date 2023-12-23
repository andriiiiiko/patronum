# Stage 1: Build Stage
FROM openjdk:17 AS build
WORKDIR /app
COPY . /app
RUN ./gradlew build | tr -d '\n'

# Stage 2: Runtime Stage
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/build/libs/quick-link-0.0.1.jar quick-link-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "quick-link-0.0.1.jar"]




