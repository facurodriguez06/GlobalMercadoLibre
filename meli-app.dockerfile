# ========== ETAPA 1: BUILD ==========
FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace

# Copiar archivos de configuración de Gradle primero (para aprovechar la caché de Docker)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar el código fuente
COPY src src

# Dar permisos y compilar
RUN chmod +x ./gradlew && \
    ./gradlew bootJar --no-daemon -x test

# ========== ETAPA 2: RUNTIME ==========
FROM eclipse-temurin:21-jre

WORKDIR /app

# Nota: EXPOSE es solo informativo, Render ignora esto y asigna su propio puerto
EXPOSE 8080

# TRUCO: Copiamos cualquier archivo .jar generado y lo renombramos a app.jar
# Así no importa si tu proyecto se llama "global", "mutant" o "meli".
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

# Ejecutar la aplicación
# Importante: Java usará la configuración de application.properties
ENTRYPOINT ["java", "-jar", "/app/app.jar"]