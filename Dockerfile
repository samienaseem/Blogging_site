# Use an official Java runtime as a parent image
FROM openjdk:17

# Copy the application JAR file into the container
COPY target/WriteAByte-0.0.1-SNAPSHOT.jar /app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
