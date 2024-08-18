# Build stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw && ./mvnw package

# Run stage
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

CMD ["sh", "-c", "java -jar app.jar --spring.datasource.url=$DB_URL --spring.datasource.username=$DB_USERNAME --spring.datasource.password=$DB_PASSWORD"]