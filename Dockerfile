# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
# If using Maven
RUN ./mvnw clean package -DskipTests
# The above command creates a JAR in /app/target

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]