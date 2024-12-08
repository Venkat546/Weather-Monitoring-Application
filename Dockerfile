# Use an official OpenJDK image as the base image
FROM openjdk:23-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the target directory to the container
COPY target/WeatherApplication-0.0.1-SNAPSHOT.jar /app/weather-application.jar

# Expose the port that the app will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/weather-application.jar"]
