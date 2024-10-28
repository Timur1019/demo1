# Первый этап: сборка jar-файла с Gradle
FROM gradle:jdk17 AS build
WORKDIR /app

# Копируем Gradle файлы и исходный код в контейнер
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Собираем jar-файл
RUN gradle bootJar --no-daemon

# Второй этап: создание финального образа с OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
