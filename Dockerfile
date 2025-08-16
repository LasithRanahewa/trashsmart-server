# Build stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -e -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]