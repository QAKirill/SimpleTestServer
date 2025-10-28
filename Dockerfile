# Стадия сборки
FROM gradle:8.14.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Стадия запуска
FROM openjdk:21-jdk-slim
WORKDIR /app

# Создаем не-root пользователя для безопасности
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Копируем JAR из стадии сборки
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]