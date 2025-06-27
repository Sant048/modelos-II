# Imagen base ligera con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Carpeta dentro del contenedor
WORKDIR /app

# Copia el JAR compilado
COPY build/libs/api-productos-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto por defecto
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
