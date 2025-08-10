FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/esource-0.0.1-SNAPSHOT.jar /app/esource-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/esource-0.0.1-SNAPSHOT.jar"]