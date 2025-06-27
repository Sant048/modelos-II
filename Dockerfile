# Etapa 1: compila el proyecto con Gradle
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# Etapa 2: imagen final
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY productos.dat productos.dat
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
