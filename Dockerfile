FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./build/libs/ChanhyungUniversity-0.0.1-SNAPSHOT.jar /app/ChanhyungUniversity-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/ChanhyungUniversity-0.0.1-SNAPSHOT.jar"]