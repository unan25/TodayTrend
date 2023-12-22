FROM eclipse-temurin:17
ARG JAR_FILE=*.jar
COPY build/libs/notification-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 13000
ENTRYPOINT ["java","-jar","/app.jar"]