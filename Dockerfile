# Первый этап: сборка jar-файла
FROM gradle:jdk17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта в контейнер
COPY src/main/java/com/example/demo .

# Выполняем сборку jar-файла
RUN gradle bootJar --no-daemon

# Второй этап: создание финального образа
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar-файл из предыдущего этапа сборки
COPY --from=build /app/build/libs/demo1-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, который использует приложение
EXPOSE 8085

# Запускаем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
