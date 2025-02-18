FROM openjdk:21-jdk

WORKDIR /app

COPY target/pruebatecnica4-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/data.sql /app/resources/data.sql



EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
