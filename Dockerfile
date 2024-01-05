FROM ubuntu:20.04

RUN apt-get update && apt-get install -y \
    openjdk-17-jre-headless \
    && rm -rf /var/lib/apt/lists/*

RUN apt-get update && apt-get install -y \
    wget \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . /app

RUN chmod +x ./gradlew

RUN ./gradlew build

EXPOSE 9999

CMD ["java", "-jar", "build/libs/quick-link-0.0.1.jar"]