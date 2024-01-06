FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/quick-link-0.0.1.jar app.jar

EXPOSE 9999

CMD ["java", "-jar", "app.jar"]
