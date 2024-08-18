# Build stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY . .

# Ensure Maven wrapper is executable
RUN chmod +x ./mvnw

# Use Maven to package the application
RUN ./mvnw clean package -e -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Run the application with environment variables for the database
CMD ["sh", "-c", "java -jar app.jar --spring.datasource.url=$DB_URL --spring.datasource.username=$DB_USERNAME --spring.datasource.password=$DB_PASSWORD"]
