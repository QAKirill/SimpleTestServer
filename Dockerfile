FROM gradle:8.14.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM builder AS extractor
WORKDIR /app
RUN find build/libs -name "*.jar" ! -name "*-plain.jar" -exec java -Djarmode=layertools -jar {} extract --destination extracted \;

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=extractor /app/extracted/dependencies/ ./
COPY --from=extractor /app/extracted/spring-boot-loader/ ./
COPY --from=extractor /app/extracted/snapshot-dependencies/ ./
COPY --from=extractor /app/extracted/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]