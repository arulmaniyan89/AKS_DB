FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/*.jar .

# Expose the port your application listens on (adjust as needed)
EXPOSE 5000

# Specify the command to run your application
CMD ["java", "-jar", "gspi_orchmanager_service-1.0.539.jar"]