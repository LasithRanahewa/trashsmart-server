# Build stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY . .
RUN ./mvnw package

# Run stage
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
