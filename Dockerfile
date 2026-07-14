# Build stage — no Maven wrapper in the repo, so use the Maven image
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# Run stage — slim JRE, just the fat jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Spring reads PORT + MONGODB_URI from the environment (see application.yml)
CMD ["java", "-jar", "app.jar"]
