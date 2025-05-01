# Stage 1: build
# Start with a Maven image that includes JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with maven
RUN mvn package -DskipTests

#Stage 2: create image
# Start with Amazon Correto JDK 21
FROM amazoncorretto:21.0.4

# Copy chứng chỉ vào container
COPY atlas-root-ca.pem /tmp/atlas-root-ca.pem

# Import vào truststore
RUN keytool -importcert \
    -alias atlas-root-ca \
    -file /tmp/atlas-root-ca.pem \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit \
    -noprompt
RUN keytool -list -alias atlas-root-ca -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]