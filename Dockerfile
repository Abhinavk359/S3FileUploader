# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy only the built jar file
COPY --from=build /app/target/ImageUploader-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Spring Boot listens on
EXPOSE 8080

# Optional: allows JVM tuning via environment variable
ENV JAVA_OPTS=""

# Use exec form to preserve signals and enable Java debugging
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
