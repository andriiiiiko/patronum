FROM debian:bullseye-slim

RUN apt-get update && apt-get install -y \
    openjdk-17-jre-headless \
    findutils \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/quick

COPY . .

RUN chmod +x gradlew

RUN ./gradlew build

CMD ["java", "-jar", "build/libs/quick-link-0.0.1.jar"]